package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object BellPepperItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "bell_pepper")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "bell_pepper")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.bell_pepper")

    override val foodNutrition: Int
        get() = 3

    override val foodSaturation: Float
        get() = 1.5F
}