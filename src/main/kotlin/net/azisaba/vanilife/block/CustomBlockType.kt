package net.azisaba.vanilife.block

import net.azisaba.vanilife.registry.Keyed
import org.bukkit.block.BlockType

interface CustomBlockType: Keyed {
    val type: BlockType
}