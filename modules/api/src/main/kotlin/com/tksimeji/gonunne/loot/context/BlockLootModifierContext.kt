package com.tksimeji.gonunne.loot.context

import com.tksimeji.gonunne.loot.context.impl.BlockLootModifierContextImpl
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface BlockLootModifierContext: LootModifierContext {
    val block: Block

    val player: Player

    val itemStack: ItemStack

    companion object {
        @JvmStatic
        fun create(block: Block, player: Player, itemStack: ItemStack): BlockLootModifierContext {
            return BlockLootModifierContextImpl(block, player, itemStack)
        }
    }
}