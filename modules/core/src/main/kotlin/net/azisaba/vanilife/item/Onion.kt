package net.azisaba.vanilife.item

import com.tksimeji.gonunne.Season
import com.tksimeji.gonunne.item.Seasonal
import com.tksimeji.gonunne.item.Vegetable
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Onion: Seasonal, Vegetable {
    override val key: Key = Key.key(PLUGIN_ID, "onion")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "onion")

    override val displayName: Component = Component.translatable("item.vanilife.onion")

    override val season: Set<Season> = setOf(Season.SPRING)

    override val nutrition: Int = 3

    override val saturation: Float = 4.1f
}