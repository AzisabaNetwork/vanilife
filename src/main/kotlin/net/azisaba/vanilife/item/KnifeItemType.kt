package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object KnifeItemType: CustomItemType {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "knife")

    override val itemType: ItemType
        get() = ItemType.STONE_SWORD

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "knife")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.knife")
}