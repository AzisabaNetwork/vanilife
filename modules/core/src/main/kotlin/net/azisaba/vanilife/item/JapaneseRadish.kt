package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season
import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object JapaneseRadish: Seasoned, Vegetable {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "japanese_radish")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "japanese_radish")

    override val displayName: Component = Component.translatable("item.vanilife.japanese_radish")

    override val season: Set<Season> = setOf(Season.WINTER)

    override val nutrition: Int = 5

    override val saturation: Float = 5.1F
}