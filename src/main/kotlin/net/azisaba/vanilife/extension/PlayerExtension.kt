package net.azisaba.vanilife.extension

import net.azisaba.vanilife.font.HudFont
import net.azisaba.vanilife.item.MoneyItemType
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.Player
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.ItemStack

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

fun Player.sendMoneyHud(money: Int, textColor: TextColor = NamedTextColor.WHITE) {
    if (remainingAir < maximumAir) {
        sendActionBar(Component.empty())
        return
    }

    sendActionBar(Component.text("${HudFont.space88}${HudFont.money}").font(HudFont)
        .append(Component.text(money.toString().padEnd(10, HudFont.space6)).color(textColor)))
}