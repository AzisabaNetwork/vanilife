package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season
import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object NappaCabbageItemType: Seasoned, Vegetable {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "nappa_cabbage")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "nappa_cabbage")

    override val displayName: Component = Component.translatable("item.vanilife.nappa_cabbage")

    override val season: Set<Season> = setOf(Season.WINTER)

    override val nutrition: Int = 5

    override val saturation: Float = 4.5F
}