package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season
import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Strawberry: Fruit, Seasoned {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "strawberry")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "strawberry")

    override val displayName: Component = Component.translatable("item.vanilife.strawberry")

    override val nutrition: Int = 4

    override val saturation: Float = 2.8F

    override val season: Set<Season> = setOf(Season.SPRING)
}