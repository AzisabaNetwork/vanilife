package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Peach: Fruit, Seasonal {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "peach")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "peach")

    override val displayName: Component = Component.translatable("item.vanilife.peach")

    override val group: ItemGroup = ItemGroups.FRUIT

    override val nutrition: Int = 6

    override val saturation: Float = 3.6F

    override val season: Set<Season> = setOf(Season.SUMMER)
}