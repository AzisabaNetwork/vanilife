package net.azisaba.vanilife.item

import net.azisaba.vanilife.block.CustomBlockType
import net.azisaba.vanilife.registry.BlockTypes
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object ChestoreItemType: BlockItemType {
    override val type: ItemType = ItemType.CHEST

    override val blockType: CustomBlockType = BlockTypes.CHESTORE

    override val title: Component = Component.translatable("block.vanilife.chestore")
}