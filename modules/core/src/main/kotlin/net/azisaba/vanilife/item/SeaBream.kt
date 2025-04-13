package net.azisaba.vanilife.item

import com.tksimeji.gonunne.Season
import com.tksimeji.gonunne.item.Fish
import com.tksimeji.gonunne.item.Seasonal
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemType

object SeaBream: Fish, Seasonal {
    override val key: Key = Key.key(PLUGIN_ID, "sea_bream")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "sea_bream")

    override val displayName: Component = Component.translatable("item.vanilife.sea_bream")

    override val rarity: ItemRarity = ItemRarity.UNCOMMON

    override val nutrition: Int = 4

    override val saturation: Float = 1.2f

    override val season: Set<Season> = setOf(Season.SPRING, Season.FALL)
}