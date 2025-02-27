package net.azisaba.vanilife.block

import net.azisaba.vanilife.item.BlockItemType
import net.azisaba.vanilife.registry.Keyed
import org.bukkit.block.BlockType

interface CustomBlockType: Keyed {
    val type: BlockType

    val item: BlockItemType?
        get() = null
}