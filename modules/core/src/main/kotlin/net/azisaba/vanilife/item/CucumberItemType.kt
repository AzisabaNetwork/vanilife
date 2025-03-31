package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object CucumberItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "cucumber")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "cucumber")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.cucumber")

    override val foodNutrition: Int
        get() = 3

    override val foodSaturation: Float
        get() = 2.5F
}