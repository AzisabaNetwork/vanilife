package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object BananaItemType: Food {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "banana")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "banana")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.banana")

    override val foodNutrition: Int
        get() = 4

    override val foodSaturation: Float
        get() = 3.4F
}