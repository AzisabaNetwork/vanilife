package net.azisaba.vanilife.extension

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.font.HudFont
import net.azisaba.vanilife.item.MoneyItemType
import net.azisaba.vanilife.playboost.PlayBoost
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

fun Player.getPlayBoost(playBoost: PlayBoost): Int {
    Vanilife.dataSource.connection.use { connection ->
        connection.prepareStatement("SELECT boost_value FROM ${Vanilife.DATABASE_PLAY_BOOST} WHERE player = ? AND boost_key = ?").use { preparedStatement ->
            preparedStatement.setString(1, uniqueId.toString())
            preparedStatement.setString(2, playBoost.key.asString())
            preparedStatement.executeQuery().use { resultSet ->
                if (!resultSet.next()) {
                    return 0
                } else {
                    return resultSet.getInt("boost_value")
                }
            }
        }
    }
}

fun Player.setPlayBoost(playBoost: PlayBoost, value: Int) {
    Vanilife.dataSource.connection.use { connection ->
        if (value <= 0) {
            connection.prepareStatement("DELETE FROM ${Vanilife.DATABASE_PLAY_BOOST} WHERE player = ? AND boost_key = ?").use { preparedStatement ->
                preparedStatement.setString(1, uniqueId.toString())
                preparedStatement.setString(2, playBoost.key.asString())
                preparedStatement.executeUpdate()
            }
        } else if (!hasPlayBoost(playBoost)) {
            connection.prepareStatement("INSERT INTO ${Vanilife.DATABASE_PLAY_BOOST} VALUES (?, ?, ?)").use { preparedStatement ->
                preparedStatement.setString(1, uniqueId.toString())
                preparedStatement.setString(2, playBoost.key.asString())
                preparedStatement.setInt(3, value)
                preparedStatement.executeUpdate()
            }
        } else {
            connection.prepareStatement("UPDATE ${Vanilife.DATABASE_PLAY_BOOST} SET boost_value = ? WHERE player = ? AND boost_key = ?").use { preparedStatement ->
                preparedStatement.setInt(1, value)
                preparedStatement.setString(2, uniqueId.toString())
                preparedStatement.setString(3, playBoost.key.asString())
                preparedStatement.executeUpdate()
            }
        }
    }
}

fun Player.hasPlayBoost(playBoost: PlayBoost): Boolean {
    Vanilife.dataSource.connection.use { connection ->
        connection.prepareStatement("SELECT EXISTS (SELECT 1 FROM ${Vanilife.DATABASE_PLAY_BOOST} WHERE player = ? AND boost_key = ?)").use { preparedStatement ->
            preparedStatement.setString(1, uniqueId.toString())
            preparedStatement.setString(2, playBoost.key.asString())
            preparedStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    val exists = resultSet.getBoolean(1)
                    return exists
                }
                return false
            }
        }
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