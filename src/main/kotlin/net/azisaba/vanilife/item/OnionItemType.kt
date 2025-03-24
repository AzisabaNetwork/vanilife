package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object OnionItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "onion")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "onion")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.onion")

    override val foodNutrition: Int
        get() = 3

    override val foodSaturation: Float
        get() = 4.1F
}