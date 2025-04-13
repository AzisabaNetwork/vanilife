package com.tksimeji.gonunne.enchantment.context

import com.tksimeji.gonunne.enchantment.context.impl.BlockBreakContextImpl
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

interface BlockBreakContext: EnchantmentContext {
    val blocks: MutableSet<Block>

    companion object {
        @JvmStatic
        fun create(player: Player, itemStack: ItemStack, equipmentSlot: EquipmentSlot, blocks: MutableSet<Block>): BlockBreakContext {
            return BlockBreakContextImpl(player, itemStack, equipmentSlot, blocks)
        }
    }
}