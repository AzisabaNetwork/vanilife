package net.azisaba.vanilife.block

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.item.BlockItemType
import net.azisaba.vanilife.registry.ItemTypes
import net.kyori.adventure.key.Key
import org.bukkit.block.BlockType

object TestBlockType: CustomBlockType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "test")

    override val type: BlockType = BlockType.DIRT

    override val itemType: BlockItemType
        get() = ItemTypes.TEST
}