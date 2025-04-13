package net.azisaba.vanilife.item

import com.tksimeji.gonunne.Season
import com.tksimeji.gonunne.item.Seasonal
import com.tksimeji.gonunne.item.Vegetable
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Lettuce: Seasonal, Vegetable {
    override val key: Key = Key.key(PLUGIN_ID, "lettuce")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "lettuce")

    override val displayName: Component = Component.translatable("item.vanilife.lettuce")

    override val season: Set<Season> = setOf(Season.SUMMER, Season.FALL)

    override val nutrition: Int = 4

    override val saturation: Float = 1.2f
}