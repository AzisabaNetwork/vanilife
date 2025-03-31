package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object SlicedTomatoItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "sliced_tomato")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "sliced_tomato")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.sliced_tomato")

    override val foodNutrition: Int
        get() = 1

    override val foodSaturation: Float
        get() = 0.25F
}