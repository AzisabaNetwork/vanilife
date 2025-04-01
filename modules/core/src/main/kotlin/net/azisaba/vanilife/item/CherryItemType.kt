package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season
import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object CherryItemType: Fruit, Seasoned {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "cherry")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "cherry")

    override val displayName: Component = Component.translatable("item.vanilife.cherry")

    override val nutrition: Int = 2

    override val saturation: Float = 2.8F

    override val season: Set<Season> = setOf(Season.SUMMER)
}