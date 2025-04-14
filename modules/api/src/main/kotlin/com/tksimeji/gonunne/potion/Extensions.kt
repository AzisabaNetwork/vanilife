package com.tksimeji.gonunne.potion

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType

fun PotionType.createItemStack(): ItemStack {
    val itemStack = ItemStack.of(Material.POTION)
    val itemMeta = itemStack.itemMeta as PotionMeta
    itemMeta.basePotionType = this
    return itemStack.apply { this.itemMeta = itemMeta }
}