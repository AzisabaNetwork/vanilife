package net.azisaba.vanilife.item

import net.azisaba.vanilife.block.CustomBlockType

interface BlockItemType: CustomItemType {
    val blockType: CustomBlockType
}