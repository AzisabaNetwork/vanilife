package net.azisaba.vanilife.loot

import org.bukkit.block.Block
import org.bukkit.block.BlockType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface BlockLootModifier: LootModifier {
    val targets: Set<BlockType>

    fun blockLoot(block: Block, player: Player, itemStack: ItemStack, loot: MutableList<ItemStack>)
}