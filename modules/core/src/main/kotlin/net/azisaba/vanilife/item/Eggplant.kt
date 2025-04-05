package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Eggplant: Seasonal, Vegetable {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "eggplant")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "eggplant")

    override val displayName: Component = Component.translatable("item.vanilife.eggplant")

    override val group: ItemGroup = ItemGroups.VEGETABLE

    override val season: Set<Season> = setOf(Season.SUMMER)

    override val nutrition: Int = 4

    override val saturation: Float = 3.5F
}