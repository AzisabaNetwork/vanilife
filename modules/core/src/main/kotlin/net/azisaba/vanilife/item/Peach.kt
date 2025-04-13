package net.azisaba.vanilife.item

import com.tksimeji.gonunne.Season
import com.tksimeji.gonunne.item.Fruit
import com.tksimeji.gonunne.item.Seasonal
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Peach: Fruit, Seasonal {
    override val key: Key = Key.key(PLUGIN_ID, "peach")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "peach")

    override val displayName: Component = Component.translatable("item.vanilife.peach")

    override val nutrition: Int = 6

    override val saturation: Float = 3.6f

    override val season: Set<Season> = setOf(Season.SUMMER)
}