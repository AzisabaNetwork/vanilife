package net.azisaba.vanilife.extension

import net.azisaba.vanilife.font.DefaultFont
import net.azisaba.vanilife.font.HudFont
import net.azisaba.vanilife.item.BillItemType
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

var Player.money: Int
    get() {
        return inventory.contents
            .filterNotNull()
            .filter { it.customItemType is BillItemType }
            .sumOf { (it.customItemType as BillItemType).price * it.amount }
    }
    set(value) {
        if (money <= value) {
            giveItemStack(*BillItemType.createItemStacks(value - money).toTypedArray())
            return
        }

        val differance = money - value
        var paid = 0

        for (bill in BillItemType.types) {
            for (itemStack in inventory.filterNotNull()) {
                if (itemStack.customItemType != bill.value) {
                    continue
                }

                while (paid < differance && ! itemStack.isEmpty) {
                    paid += bill.key
                    itemStack.amount -= 1
                }
            }
        }

        if (differance < paid) {
            money = value
        }
    }

fun Player.giveItemStack(vararg itemStacks: ItemStack) {
    val remaining = mutableListOf<ItemStack>()
    itemStacks.forEach { remaining.addAll(inventory.addItem(it).values) }

    for (itemStack in remaining) {
        world.dropItem(location, itemStack)
    }
}

fun Player.sendError(message: Component) {
    sendMessage(
        Component.text()
        .append(Component.text(DefaultFont.ERROR).hoverEvent(HoverEvent.showText(Component.text("エラー"))))
        .append(message.colorIfAbsent(NamedTextColor.RED)))
}

fun Player.sendInfo(message: Component) {
    sendMessage(
        Component.text()
        .append(Component.text(DefaultFont.INFO).hoverEvent(HoverEvent.showText(Component.text("情報"))))
        .append(message))
}

fun Player.sendMoneyHud(money: Int, color: TextColor = NamedTextColor.WHITE) {
    if (remainingAir < maximumAir) {
        sendActionBar(Component.empty())
        return
    }

    sendActionBar(Component.text("${HudFont.space88}${HudFont.money}").font(HudFont)
        .append(Component.text(money.toString().padEnd(10, HudFont.space6)).color(color)))
}