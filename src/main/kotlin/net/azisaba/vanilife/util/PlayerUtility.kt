package net.azisaba.vanilife.util

import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import java.util.UUID

private val armorStandMap = mutableMapOf<UUID, ArmorStand>()

fun Player.getArmorStand(): ArmorStand {
    return armorStandMap[uniqueId]!!
}

fun Player.setArmorStand(armorStand: ArmorStand) {
    armorStandMap[uniqueId] = armorStand.also {
        it.isCollidable = false
        it.isInvisible = true
        it.setGravity(false)
    }
}