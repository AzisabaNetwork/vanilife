package com.tksimeji.gonunne.loot

import com.tksimeji.gonunne.loot.context.BlockLootModifierContext
import org.bukkit.block.BlockType

interface BlockLootModifier: LootModifier<BlockType, BlockLootModifierContext> {
}