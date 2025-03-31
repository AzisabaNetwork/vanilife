package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object FruitSandwichItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "fruit_sandwich")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "fruit_sandwich")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.fruit_sandwich")

    override val foodNutrition: Int
        get() = 7

    override val foodSaturation: Float
        get() = 5.9F
}