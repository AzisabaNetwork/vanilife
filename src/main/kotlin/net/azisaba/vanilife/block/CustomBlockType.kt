package net.azisaba.vanilife.block

import net.azisaba.vanilife.item.BlockItemType
import net.azisaba.vanilife.registry.Keyed
import org.bukkit.block.Block
import org.bukkit.block.BlockType

interface CustomBlockType: Keyed {
    val type: BlockType

    val itemType: BlockItemType?
        get() = null

    fun onPlace(block: Block) {
    }

    fun onBreak(block: Block) {
    }
}