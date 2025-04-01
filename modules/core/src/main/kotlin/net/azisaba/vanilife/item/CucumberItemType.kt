package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season
import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object CucumberItemType: Seasoned, Vegetable {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "cucumber")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "cucumber")

    override val displayName: Component = Component.translatable("item.vanilife.cucumber")

    override val season: Set<Season> = setOf(Season.SUMMER)

    override val nutrition: Int = 3

    override val saturation: Float = 2.5F
}