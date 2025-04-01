package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object BaconItemType: Food {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "bacon")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "bacon")

    override val displayName: Component = Component.translatable("item.vanilife.bacon")

    override val nutrition: Int = 8

    override val saturation: Float = 13.8F
}