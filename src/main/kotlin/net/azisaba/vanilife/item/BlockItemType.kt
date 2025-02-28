package net.azisaba.vanilife.item

import net.azisaba.vanilife.block.CustomBlockType
import net.kyori.adventure.key.Key

interface BlockItemType: CustomItemType {
    override val key: Key
        get() = blockType.key

    val blockType: CustomBlockType
}