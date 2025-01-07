package net.azisaba.vanilife.util

import net.azisaba.vanilife.Vanilife
import org.bukkit.NamespacedKey
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import java.util.UUID

fun Player.getMoney(): Int {
    return persistentDataContainer.getOrDefault(NamespacedKey(Vanilife.plugin(), "money"), PersistentDataType.INTEGER, 0)
}

fun Player.setMoney(money: Int) {
    persistentDataContainer.set(NamespacedKey(Vanilife.plugin(), "money"), PersistentDataType.INTEGER, money)
}

private val armorStandMap = mutableMapOf<UUID, ArmorStand>()

fun Player.getArmorStand(): ArmorStand {
    return armorStandMap[uniqueId]!!
}

fun Player.setArmorStand(armorStand: ArmorStand) {
    armorStandMap[uniqueId] = armorStand.also {
        it.isCollidable = false
        it.isCustomNameVisible = false
        it.isInvisible = true
        it.canPickupItems
        it.setGravity(false)
    }
}