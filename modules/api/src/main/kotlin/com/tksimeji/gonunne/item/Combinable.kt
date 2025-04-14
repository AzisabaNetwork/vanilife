package com.tksimeji.gonunne.item

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface Combinable: CustomItemType {
    val combineHint: Component

    fun combine(player: Player, itemStack: ItemStack, other: ItemStack)

    fun canCombine(other: ItemStack): Boolean
}