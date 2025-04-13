package net.azisaba.vanilife.item

import com.tksimeji.gonunne.item.Food
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object ButteredPotato: Food {
    override val key: Key = Key.key(PLUGIN_ID, "buttered_potato")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "buttered_potato")

    override val displayName: Component = Component.translatable("item.vanilife.buttered_potato")

    override val nutrition: Int = 6

    override val saturation: Float = 7f
}