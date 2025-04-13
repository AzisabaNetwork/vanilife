package net.azisaba.vanilife.item

import com.tksimeji.gonunne.Season
import com.tksimeji.gonunne.item.Fish
import com.tksimeji.gonunne.item.Seasonal
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object HorseMackerel: Fish, Seasonal {
    override val key: Key = Key.key(PLUGIN_ID, "horse_mackerel")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "horse_mackerel")

    override val displayName: Component = Component.translatable("item.vanilife.horse_mackerel")

    override val nutrition: Int = 3

    override val saturation: Float = 0.6f

    override val season: Set<Season> = setOf(Season.SPRING, Season.SUMMER)
}