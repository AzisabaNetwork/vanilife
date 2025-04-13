package net.azisaba.vanilife.item

import com.tksimeji.gonunne.item.Fish
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemType

object Tuna: Fish {
    override val key: Key = Key.key(PLUGIN_ID, "tuna")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "tuna")

    override val displayName: Component = Component.translatable("item.vanilife.tuna")

    override val rarity: ItemRarity = ItemRarity.RARE

    override val nutrition: Int = 6

    override val saturation: Float = 8.2f
}