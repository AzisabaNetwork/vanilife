package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Grape: Fruit, Seasonal {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "grape")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "grape")

    override val displayName: Component = Component.translatable("item.vanilife.grape")

    override val group: ItemGroup = ItemGroups.FRUIT

    override val nutrition: Int = 4

    override val saturation: Float = 2.8F

    override val season: Set<Season> = setOf(Season.FALL)
}