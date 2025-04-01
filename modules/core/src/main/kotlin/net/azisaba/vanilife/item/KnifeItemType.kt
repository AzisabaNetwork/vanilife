package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object KnifeItemType: CustomItemType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "knife")

    override val itemType: ItemType = ItemType.STONE_SWORD

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "knife")

    override val displayName: Component = Component.translatable("item.vanilife.knife")
}