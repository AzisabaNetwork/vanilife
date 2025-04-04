package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Sandwich: Food {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "sandwich")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "sandwich")

    override val displayName: Component = Component.translatable("item.vanilife.sandwich")

    override val nutrition: Int = 7

    override val saturation: Float = 6.8F
}