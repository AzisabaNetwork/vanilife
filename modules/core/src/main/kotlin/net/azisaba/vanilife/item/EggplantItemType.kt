package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object EggplantItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "eggplant")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "eggplant")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.eggplant")

    override val foodNutrition: Int
        get() = 4

    override val foodSaturation: Float
        get() = 3.5F
}