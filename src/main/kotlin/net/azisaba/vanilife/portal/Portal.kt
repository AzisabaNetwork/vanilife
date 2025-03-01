package net.azisaba.vanilife.portal

import net.azisaba.vanilife.registry.Keyed

import org.bukkit.block.Block
import org.bukkit.block.BlockType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface Portal: Keyed {
    val item: ItemStack

    val fluidType: BlockType

    val frameType: BlockType

    fun onCreate(fluids: Set<Block>) {
    }

    fun onPlayerPortal(player: Player) {
    }
}