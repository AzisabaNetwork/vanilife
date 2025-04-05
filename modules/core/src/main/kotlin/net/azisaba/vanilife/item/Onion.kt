package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Onion: Seasonal, Vegetable {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "onion")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "onion")

    override val displayName: Component = Component.translatable("item.vanilife.onion")

    override val group: ItemGroup = ItemGroups.VEGETABLE

    override val season: Set<Season> = setOf(Season.SPRING)

    override val nutrition: Int = 3

    override val saturation: Float = 4.1F
}