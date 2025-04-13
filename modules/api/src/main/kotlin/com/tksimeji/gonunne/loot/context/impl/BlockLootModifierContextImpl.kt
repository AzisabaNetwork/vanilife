package com.tksimeji.gonunne.loot.context.impl

import com.tksimeji.gonunne.loot.context.BlockLootModifierContext
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

internal class BlockLootModifierContextImpl(override val block: Block, override val player: Player, override val itemStack: ItemStack): BlockLootModifierContext {
}