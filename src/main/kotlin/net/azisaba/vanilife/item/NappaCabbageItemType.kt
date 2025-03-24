package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object NappaCabbageItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "nappa_cabbage")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "nappa_cabbage")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.nappa_cabbage")

    override val foodNutrition: Int
        get() = 5

    override val foodSaturation: Float
        get() = 4.5F
}