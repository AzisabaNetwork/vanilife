package com.tksimeji.gonunne.enchantment.context

import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

interface EnchantmentContext {
    val player: Player

    val itemStack: ItemStack

    val equipmentSlot: EquipmentSlot
}