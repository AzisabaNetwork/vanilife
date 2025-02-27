package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.block.CustomBlockType
import net.azisaba.vanilife.registry.BlockTypes
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.inventory.ItemType

object TestBlockItemType: BlockItemType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "test")

    override val type: ItemType = ItemType.DIRT

    override val blockType: CustomBlockType = BlockTypes.TEST

    override val title: Component = Component.text("Test Block").color(NamedTextColor.LIGHT_PURPLE)
}