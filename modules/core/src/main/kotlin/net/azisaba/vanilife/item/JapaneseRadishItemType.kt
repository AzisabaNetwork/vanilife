package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object JapaneseRadishItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "japanese_radish")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "japanese_radish")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.japanese_radish")

    override val foodNutrition: Int
        get() = 5

    override val foodSaturation: Float
        get() = 5.1F
}