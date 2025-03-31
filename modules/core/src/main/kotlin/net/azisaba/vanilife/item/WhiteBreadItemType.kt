package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object WhiteBreadItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "white_bread")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "white_bread")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.white_bread")

    override val foodNutrition: Int
        get() = 6

    override val foodSaturation: Float
        get() = 6.5F
}