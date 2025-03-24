package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object CherryItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "cherry")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "cherry")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.cherry")

    override val foodNutrition: Int
        get() = 2

    override val foodSaturation: Float
        get() = 2.8F
}