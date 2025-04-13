package net.azisaba.vanilife.item

import com.tksimeji.gonunne.item.Food
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object SlicedTomato: Food {
    override val key: Key = Key.key(PLUGIN_ID, "sliced_tomato")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "sliced_tomato")

    override val displayName: Component = Component.translatable("item.vanilife.sliced_tomato")

    override val nutrition: Int = 1

    override val saturation: Float = 0.25f
}