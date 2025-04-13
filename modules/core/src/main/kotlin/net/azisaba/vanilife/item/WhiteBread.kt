package net.azisaba.vanilife.item

import com.tksimeji.gonunne.item.Food
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object WhiteBread: Food {
    override val key: Key = Key.key(PLUGIN_ID, "white_bread")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "white_bread")

    override val displayName: Component = Component.translatable("item.vanilife.white_bread")

    override val nutrition: Int = 6

    override val saturation: Float = 6.5f
}