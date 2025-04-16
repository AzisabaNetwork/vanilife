package net.azisaba.vanilife.extensions

import com.tksimeji.gonunne.component.plainText
import com.tksimeji.gonunne.font.font
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.font.DialogueFirstLineFont
import net.azisaba.vanilife.font.DialogueSecondLineFont
import net.azisaba.vanilife.font.HudFont
import net.azisaba.vanilife.font.TitleFont
import net.azisaba.vanilife.item.Money
import net.azisaba.vanilife.util.runTaskLaterAsync
import net.azisaba.vanilife.util.runTaskTimer
import net.azisaba.vanilife.util.runTaskTimerAsync
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.ShadowColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.title.Title
import net.kyori.adventure.translation.GlobalTranslator
import org.bukkit.OfflinePlayer
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.ItemStack
import java.time.Duration
import java.util.*
import kotlin.random.Random

private val DATABASE_CACHE_LEVEL = mutableMapOf<UUID, Int>()

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
                        return resultSet.getInt("level").also {
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
            connection.prepareStatement("INSERT INTO ${Vanilife.DATABASE_PLAYER} (uuid, level) VALUES (?, ?) ON DUPLICATE KEY UPDATE level = ?").use { preparedStatement ->
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
            .filter { it.customItemType() is Money }
            .sumOf { (it.customItemType() as Money).price * it.amount }
        val moneyOnCursor = itemOnCursor.takeIf { it.customItemType() is Money }
            ?.run { (itemOnCursor.customItemType() as Money).price * itemOnCursor.amount } ?: 0
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
                if (itemStack.customItemType() != moneyType) {
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

fun Player.sendHud(level: Component, levelIcon: Char = HudFont.levelIcon(lv), money: Component, moneyIcon: Char = HudFont.MONEY) {
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

fun Player.showDialogue(component: Component, background: Char = TitleFont.DIALOGUE, also: () -> Unit = {}) {
    val string = GlobalTranslator.render(component, locale()).plainText()
    if (string.length <= 36) {
        showDialogue(component, Component.empty(), background, also)
        return
    }

    val separators = setOf('.', ',', '。', '、')
    val substring = string.take(36)
    var splitIndex = -1

    for (i in substring.length - 1 downTo 0) {
        if (substring[i] in separators) {
            splitIndex = i
            break
        }
    }

    val firstLine = Component.text(if (splitIndex != -1) string.substring(0, splitIndex + 1) else string.take(36))
    val secondLine = Component.text(if (splitIndex != -1) string.substring(splitIndex + 1) else string.drop(36))
    showDialogue(firstLine, secondLine, background, also)
}

fun Player.showDialogue(firstLine: Component, secondLine: Component, background: Char = TitleFont.DIALOGUE, also: () -> Unit = {}) {
    val firstLineString = GlobalTranslator.render(firstLine, locale()).plainText()
    val secondLineString = GlobalTranslator.render(secondLine, locale()).plainText()

    var tick = 0

    runTaskTimerAsync(0, 2) {
        val typewriterEffect = typewriterEffect(firstLineString, secondLineString, tick)
        playSound(this, Sound.BLOCK_NOTE_BLOCK_BIT, 0.8f, 1.6f + Random.nextFloat() * (2f - 1.6f))
        showDialogueDirect(typewriterEffect.first, typewriterEffect.second, background)
        tick++
        val b = typewriterEffect != Pair(firstLineString, secondLineString)
        if (!b) runTaskLaterAsync(30) { also() }
        return@runTaskTimerAsync b
    }
}

fun Player.showCharacterDialogue(component: Component, characterBackground: Char, also: () -> Unit = {}) {
    val space = " ".repeat(12)
    showDialogue(Component.text(space).append(component), characterBackground, also)
}

fun Player.showCharacterDialogue(firstLine: Component, secondLine: Component, characterBackground: Char, also: () -> Unit = {}) {
    val space = " ".repeat(12)
    showDialogue(Component.text(space).append(firstLine), Component.text(space).append(secondLine), characterBackground, also)
}

private fun Player.showDialogueDirect(firstLine: String, secondLine: String, background: Char) {
    fun buildLine(string: String): String {
        val width = string.sumOf { if (it.code in 0x00..0x7F) 2 else 4.toInt() }
        val remaining = 4 * 32 - width
        return if (remaining <= 0) {
            string
        } else {
            string + DialogueFirstLineFont.SPACE_4.toString().repeat(remaining / 4) + if (remaining % 4 == 2) DialogueFirstLineFont.SPACE_2 else ""
        }
    }

    val title = Component.text(background).font(TitleFont).shadowColor(ShadowColor.none())
    val subtitle = Component.text().color(NamedTextColor.WHITE).shadowColor(ShadowColor.none())
        .append(Component.text(buildLine(firstLine)).font(DialogueFirstLineFont))
        .append(Component.text(buildLine(secondLine)).font(DialogueSecondLineFont))
        .build()
    showTitle(Title.title(title, subtitle, Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ZERO)))
}

private fun typewriterEffect(firstLine: String, secondLine: String, tick: Int): Pair<String, String> {
    fun getDisplayText(string: String, tick: Int): String {
        var nonSpaceCount = 0
        val pos = string.indices.firstOrNull {
            if (!string[it].isWhitespace()) nonSpaceCount++
            return@firstOrNull nonSpaceCount > tick
        } ?: string.length
        return string.substring(0, pos)
    }

    val firstLineNonSpaceCount = firstLine.count { !it.isWhitespace() }
    return if (tick < firstLineNonSpaceCount) {
        Pair(getDisplayText(firstLine, tick), "")
    } else {
        Pair(firstLine, getDisplayText(secondLine, tick - firstLineNonSpaceCount))
    }
}

fun Player.focusItemStack(condition: (ItemStack) -> Boolean, focus: (Int) -> Unit = { index -> focusHotbarSlot(index) }): Boolean {
    for (index in 0..8) {
        val itemStack = inventory.getItem(index) ?: continue
        if (condition(itemStack)) {
            inventory.heldItemSlot = index
            focus(index)
            return true
        }
    }
    for (index in 9 until inventory.size) {
        val itemStack = inventory.getItem(index) ?: continue
        if (condition(itemStack)) {
            var hotbarIndex = -1

            for (index2 in 0..8) {
                if (inventory.getItem(index2) == null) {
                    hotbarIndex = index2
                    break
                }
            }

            if (hotbarIndex != -1) {
                inventory.setItem(hotbarIndex, itemStack)
            } else {
                val temp = inventory.getItem(0)
                inventory.setItem(0, itemStack)
                inventory.setItem(index, temp)
            }
            return focusItemStack(condition, focus)
        }
    }
    return false
}

fun Player.focusHotbarSlot(index: Int, color: TextColor = NamedTextColor.YELLOW, period: Long = 6, times: Int = 20, task: (Int) -> Unit = {}, also: (ItemStack?) -> Unit = {}) {
    val itemStack = inventory.getItem(index)
    val title = Component.text(TitleFont.SPACE_HOTBAR.toString().repeat(9).replaceRange(index, index + 1, TitleFont.FOCUSED_SLOT.toString()))
        .font(TitleFont)
        .shadowColor(ShadowColor.none())
    var count = 0
    runTaskTimer(0, period) {
        showTitle(Title.title(title.color(if (count % 2 == 0) color else NamedTextColor.WHITE),
            Component.empty(),
            Title.Times.times(Duration.ZERO, Duration.ofMillis(500), Duration.ZERO))
        )
        task(count)
        val b = count++ < times
        if (!b) also(itemStack)
        return@runTaskTimer b
    }
}