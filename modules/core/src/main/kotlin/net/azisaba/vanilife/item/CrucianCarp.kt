package net.azisaba.vanilife.item

import com.tksimeji.gonunne.Season
import com.tksimeji.gonunne.item.Fish
import com.tksimeji.gonunne.item.Seasonal
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object CrucianCarp: Fish, Seasonal {
    override val key: Key = Key.key(PLUGIN_ID, "crucian_carp")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "crucian_carp")

    override val displayName: Component = Component.translatable("item.vanilife.crucian_carp")

    override val nutrition: Int = 2

    override val saturation: Float = 1.2f

    override val season: Set<Season> = setOf(Season.WINTER)
}