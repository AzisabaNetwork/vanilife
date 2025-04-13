package net.azisaba.vanilife.item

import com.tksimeji.gonunne.Season
import com.tksimeji.gonunne.item.Seasonal
import com.tksimeji.gonunne.item.Vegetable
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Eggplant: Seasonal, Vegetable {
    override val key: Key = Key.key(PLUGIN_ID, "eggplant")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "eggplant")

    override val displayName: Component = Component.translatable("item.vanilife.eggplant")

    override val season: Set<Season> = setOf(Season.SUMMER)

    override val nutrition: Int = 4

    override val saturation: Float = 3.5f
}