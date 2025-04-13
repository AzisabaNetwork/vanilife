package net.azisaba.vanilife.item

import com.tksimeji.gonunne.item.Fruit
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object Banana: Fruit {
    override val key: Key = Key.key(PLUGIN_ID, "banana")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "banana")

    override val displayName: Component = Component.translatable("item.vanilife.banana")

    override val nutrition: Int = 4

    override val saturation: Float = 3.4f
}