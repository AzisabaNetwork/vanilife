package net.azisaba.vanilife.extension

import io.papermc.paper.adventure.PaperAdventure
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.chapter.Chapter
import net.azisaba.vanilife.chapter.Objective
import net.azisaba.vanilife.font.HudFont
import net.azisaba.vanilife.item.MoneyItemType
import net.azisaba.vanilife.registry.Chapters
import net.azisaba.vanilife.util.runTaskLater
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.format.ShadowColor
import net.kyori.adventure.text.format.TextDecoration
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementProgress
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.AdvancementType
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.DisplayInfo
import net.minecraft.advancements.critereon.ImpossibleTrigger
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket
import net.minecraft.resources.ResourceLocation
import org.bukkit.OfflinePlayer
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.ItemStack
import java.util.*

private val SCORE_CACHE = mutableMapOf<UUID, Int>()

var OfflinePlayer.score: Int
    get() {
        if (SCORE_CACHE.containsKey(uniqueId)) {
            return SCORE_CACHE[uniqueId]!!
        }

        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("SELECT score FROM ${Vanilife.DATABASE_PLAYER} WHERE uuid = ?").use { preparedStatement ->
                preparedStatement.setString(1, uniqueId.toString())
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        return resultSet.getInt("score").also {
                            SCORE_CACHE[uniqueId] = it
                        }
                    }
                }
            }

            connection.prepareStatement("INSERT INTO ${Vanilife.DATABASE_PLAYER} VALUES (?, ?)").use { preparedStatement ->
                preparedStatement.setString(1, uniqueId.toString())
                preparedStatement.setInt(2, 0)
                preparedStatement.executeUpdate()
                SCORE_CACHE[uniqueId] = 0
                return 0
            }
        }
    }
    set(value) {
        if (value == score || value < 0 || value > maximumScore) {
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

        SCORE_CACHE[uniqueId] = value
    }

val OfflinePlayer.scoreLevel: Int
    get() = score / 100

val OfflinePlayer.maximumScore: Int
    get() = 99 * 100

var Player.money: Int
    get() {
        val itemStacks = (openInventory.topInventory.takeIf { it is CraftingInventory }?.run { (openInventory.topInventory as CraftingInventory).matrix } ?: emptyArray<ItemStack?>()) + inventory.contents

        val money = itemStacks.filterNotNull()
            .filter { it.customItemType is MoneyItemType }
            .sumOf { (it.customItemType as MoneyItemType).price * it.amount }
        val moneyOnCursor = itemOnCursor.takeIf { it.customItemType is MoneyItemType }
            ?.run { (itemOnCursor.customItemType as MoneyItemType).price * itemOnCursor.amount } ?: 0
        return money + moneyOnCursor
    }
    set(value) {
        if (money <= value) {
            give(*MoneyItemType.createItemStacks(value - money).toTypedArray())
            return
        }

        val difference = money - value
        var paid = 0

        for ((moneyPrice, moneyType) in MoneyItemType.TYPES) {
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
    get() = Chapters.values.filter { it.isGranted(this) }.toSet()

val Player.objectives: Set<Objective>
    get() = chapters.flatMap { it.objectives.filter { objective -> objective.isAchieved(this) } }.toSet()

fun Player.sendHud(level: ComponentLike, levelIcon: Char = HudFont.levelIcon(scoreLevel), money: ComponentLike, moneyIcon: Char = HudFont.MONEY) {
    if (remainingAir < maximumAir) {
        sendActionBar(Component.empty())
        return
    }

    sendActionBar(Component.text("${HudFont.SPACE88}").font(HudFont)
        .append(Component.text(levelIcon))
        .append(Component.text(level.plainText().padEnd(3, HudFont.SPACE6)).style(level.asComponent().style()).decorationIfAbsent(TextDecoration.BOLD, TextDecoration.State.TRUE))
        .append(Component.text(moneyIcon)
        .append(Component.text(money.plainText().padEnd(10, HudFont.SPACE6)).style(money.asComponent().style()).shadowColorIfAbsent(ShadowColor.shadowColor(48, 23, 2, 255)))))
}

fun Player.sendToast(icon: ItemStack, message: ComponentLike) {
    val resourceLocation = ResourceLocation.fromNamespaceAndPath(Vanilife.PLUGIN_ID, UUID.randomUUID().toString())

    val nmsIcon = CraftItemStack.asNMSCopy(icon)
    val nmsMessage = PaperAdventure.asVanilla(message.asComponent())

    val criteria = mapOf(Pair("impossible", Criterion(ImpossibleTrigger(), ImpossibleTrigger.TriggerInstance())))
    val requirements = AdvancementRequirements(listOf(listOf("impossible")))

    val displayInfo = DisplayInfo(nmsIcon, nmsMessage, net.minecraft.network.chat.Component.empty(), Optional.empty(), AdvancementType.TASK, true, false, true)
    val advancement = Advancement(Optional.empty(), Optional.of(displayInfo), AdvancementRewards(0, emptyList(), emptyList(), Optional.empty()), criteria, requirements, false)

    val progress = AdvancementProgress().apply {
        update(requirements)
        getCriterion("impossible")!!.grant()
    }

    val connection = (this as CraftPlayer).handle.connection

    connection.send(ClientboundUpdateAdvancementsPacket(false, listOf(AdvancementHolder(resourceLocation, advancement)), emptySet(), mapOf(Pair(resourceLocation, progress))))

    runTaskLater(1) {
        connection.send(ClientboundUpdateAdvancementsPacket(false, emptyList(), setOf(resourceLocation), emptyMap()))
    }
}