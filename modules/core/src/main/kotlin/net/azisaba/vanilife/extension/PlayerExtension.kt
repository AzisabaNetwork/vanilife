package net.azisaba.vanilife.extension

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.chapter.Chapter
import net.azisaba.vanilife.chapter.Objective
import net.azisaba.vanilife.font.HudFont
import net.azisaba.vanilife.item.Money
import net.azisaba.vanilife.registry.Chapters
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.format.ShadowColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.ItemStack
import java.util.*

private val DATABASE_CACHE_LEVEL = mutableMapOf<UUID, Int>()
private val DATABASE_CACHE_CHAPTER = mutableMapOf<Pair<UUID, Chapter>, Boolean>()
private val DATABASE_CACHE_OBJECTIVE = mutableMapOf<Pair<UUID, Objective>, Boolean>()

var OfflinePlayer.lv: Int
    get() {
        if (DATABASE_CACHE_LEVEL.containsKey(uniqueId)) {
            return DATABASE_CACHE_LEVEL[uniqueId]!!
        }

        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("SELECT level FROM ${Vanilife.DATABASE_PLAYER} WHERE uuid = ?").use { preparedStatement ->
                preparedStatement.setString(1, uniqueId.toString())
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return resultSet.getInt("score").also {
                            DATABASE_CACHE_LEVEL[uniqueId] = it
                        }
                    }
                }
            }

            connection.prepareStatement("INSERT INTO ${Vanilife.DATABASE_PLAYER} VALUES (?, ?)").use { preparedStatement ->
                preparedStatement.setString(1, uniqueId.toString())
                preparedStatement.setInt(2, 1)
                preparedStatement.executeUpdate()
                DATABASE_CACHE_LEVEL[uniqueId] = 1
                return 0
            }
        }
    }
    set(value) {
        if (value == lv || value < 0 || value > 99) {
            return
        }

        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("INSERT INTO ${Vanilife.DATABASE_PLAYER} (uuid, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score = ?").use { preparedStatement ->
                preparedStatement.setString(1, uniqueId.toString())
                preparedStatement.setInt(2, value)
                preparedStatement.setInt(3, value)
                preparedStatement.executeUpdate()
            }
        }

        DATABASE_CACHE_LEVEL[uniqueId] = value
    }

var Player.money: Int
    get() {
        val itemStacks = (openInventory.topInventory.takeIf { it is CraftingInventory }?.run { (openInventory.topInventory as CraftingInventory).matrix } ?: emptyArray<ItemStack?>()) + inventory.contents

        val money = itemStacks.filterNotNull()
            .filter { it.customItemType is Money }
            .sumOf { (it.customItemType as Money).price * it.amount }
        val moneyOnCursor = itemOnCursor.takeIf { it.customItemType is Money }
            ?.run { (itemOnCursor.customItemType as Money).price * itemOnCursor.amount } ?: 0
        return money + moneyOnCursor
    }
    set(value) {
        if (money <= value) {
            give(*Money.createItemStacks(value - money).toTypedArray())
            return
        }

        val difference = money - value
        var paid = 0

        for ((moneyPrice, moneyType) in Money.TYPES) {
            for (itemStack in inventory.filterNotNull()) {
                if (itemStack.customItemType != moneyType) {
                    continue
                }

                while (paid < difference && !itemStack.isEmpty) {
                    paid += moneyPrice
                    itemStack.amount -= 1
                }
            }
        }

        if (difference < paid) {
            money = value
        }
    }

val OfflinePlayer.chapters: Set<Chapter>
    get() = Chapters.filter { hasChapter(it) }.toSet()

fun Player.sendHud(level: ComponentLike, levelIcon: Char = HudFont.levelIcon(lv), money: ComponentLike, moneyIcon: Char = HudFont.MONEY) {
    if (remainingAir < maximumAir) {
        sendActionBar(Component.empty())
        return
    }

    sendActionBar(Component.text("${HudFont.SPACE119}").font(HudFont)
        .append(Component.text(levelIcon))
        .append(Component.text(level.plainText().padEnd(3, HudFont.SPACE6)).style(level.asComponent().style()).decorationIfAbsent(TextDecoration.BOLD, TextDecoration.State.TRUE))
        .append(Component.text(moneyIcon)
        .append(Component.text(money.plainText().padEnd(10, HudFont.SPACE6)).style(money.asComponent().style()).shadowColorIfAbsent(ShadowColor.shadowColor(48, 23, 2, 255)))))
}

fun Player.sendFishingHud(distance: Double, cps: Int) {
    if (remainingAir < maximumAir) {
        sendActionBar(Component.empty())
        return
    }

    sendActionBar(Component.text("${HudFont.SPACE124}").font(HudFont)
        .append(Component.text(HudFont.FISHING_HOOK))
        .append(Component.text("${String.format("%.2f", distance).padStart(5, '0')}M"))
        .append(Component.text(HudFont.SPACE6))
        .append(Component.text(HudFont.FISHING_REEL))
        .append(Component.text(cps.toString().padStart(2, '0'))))
}

fun OfflinePlayer.grantChapter(chapter: Chapter) {
    val cacheKey = Pair(uniqueId, chapter)

    if (DATABASE_CACHE_CHAPTER[cacheKey] == true || hasChapter(chapter)) {
        return
    }

    Vanilife.dataSource.connection.use { connection ->
        connection.prepareStatement("INSERT INTO ${Vanilife.DATABASE_PLAYER_CHAPTER} VALUES (?, ?)").use { preparedStatement ->
            preparedStatement.setString(1, uniqueId.toString())
            preparedStatement.setString(2, chapter.key.asString())
            preparedStatement.executeUpdate()
        }
    }

    DATABASE_CACHE_CHAPTER[cacheKey] = true
}

fun OfflinePlayer.revokeChapter(chapter: Chapter) {
    val cacheKey = Pair(uniqueId, chapter)

    if (DATABASE_CACHE_CHAPTER[cacheKey] == false || !hasChapter(chapter)) {
        return
    }

    Vanilife.dataSource.connection.use { connection ->
        connection.prepareStatement("DELETE FROM ${Vanilife.DATABASE_PLAYER_CHAPTER} WHERE player = ? AND chapter = ?").use { preparedStatement ->
            preparedStatement.setString(1, uniqueId.toString())
            preparedStatement.setString(2, chapter.key.asString())
            preparedStatement.executeUpdate()
        }
    }

    DATABASE_CACHE_CHAPTER[cacheKey] = false
}

fun OfflinePlayer.hasChapter(chapter: Chapter): Boolean {
    val cacheKey = Pair(uniqueId, chapter)
    val cache = DATABASE_CACHE_CHAPTER[cacheKey]

    if (cache != null) {
        return cache
    }

    Vanilife.dataSource.connection.use { connection ->
        connection.prepareStatement("SELECT 1 FROM ${Vanilife.DATABASE_PLAYER_CHAPTER} WHERE player = ? AND chapter = ?").use { preparedStatement ->
            preparedStatement.setString(1, uniqueId.toString())
            preparedStatement.setString(2, chapter.key.asString())
            preparedStatement.executeQuery().use { resultSet ->
                val exists = resultSet.next()
                DATABASE_CACHE_CHAPTER[cacheKey] = exists
                return exists
            }
        }
    }
}

fun OfflinePlayer.grantObjective(objective: Objective) {
    val cacheKey = Pair(uniqueId, objective)

    if (DATABASE_CACHE_OBJECTIVE[cacheKey] == true || hasObjective(objective)) {
        return
    }

    Vanilife.dataSource.connection.use { connection ->
        connection.prepareStatement("INSERT INTO ${Vanilife.DATABASE_PLAYER_OBJECTIVE} VALUES (?, ?)").use { preparedStatement ->
            preparedStatement.setString(1, uniqueId.toString())
            preparedStatement.setString(2, objective.key.asString())
            preparedStatement.executeUpdate()
        }
    }

    DATABASE_CACHE_OBJECTIVE[cacheKey] = true
}

fun OfflinePlayer.revokeObjective(objective: Objective) {
    val cacheKey = Pair(uniqueId, objective)

    if (DATABASE_CACHE_OBJECTIVE[cacheKey] == false || !hasObjective(objective)) {
        return
    }

    Vanilife.dataSource.connection.use { connection ->
        connection.prepareStatement("DELETE FROM ${Vanilife.DATABASE_PLAYER_OBJECTIVE} WHERE player = ? AND objective = ?").use { preparedStatement ->
            preparedStatement.setString(1, uniqueId.toString())
            preparedStatement.setString(2, objective.key.asString())
            preparedStatement.executeUpdate()
        }
    }

    DATABASE_CACHE_OBJECTIVE[cacheKey] = false
}

fun OfflinePlayer.hasObjective(objective: Objective): Boolean {
    val cacheKey = Pair(uniqueId, objective)
    val cache = DATABASE_CACHE_OBJECTIVE[cacheKey]

    if (cache != null) {
        return cache
    }

    Vanilife.dataSource.connection.use { connection ->
        connection.prepareStatement("SELECT 1 FROM ${Vanilife.DATABASE_PLAYER_OBJECTIVE} WHERE player = ? AND objective = ?").use { preparedStatement ->
            preparedStatement.setString(1, uniqueId.toString())
            preparedStatement.setString(2, objective.key.asString())
            preparedStatement.executeQuery().use { resultSet ->
                val exists = resultSet.next()
                DATABASE_CACHE_OBJECTIVE[cacheKey] = exists
                return exists
            }
        }
    }
}