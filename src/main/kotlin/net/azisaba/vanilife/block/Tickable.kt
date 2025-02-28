package net.azisaba.vanilife.block

import org.bukkit.block.Block

interface Tickable: CustomBlockType {
    fun tick(block: Block) {
    }
}