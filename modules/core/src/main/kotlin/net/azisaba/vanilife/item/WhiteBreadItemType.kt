package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object WhiteBreadItemType: Food {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "white_bread")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "white_bread")

    override val displayName: Component = Component.translatable("item.vanilife.white_bread")

    override val nutrition: Int = 6

    override val saturation: Float = 6.5F
}