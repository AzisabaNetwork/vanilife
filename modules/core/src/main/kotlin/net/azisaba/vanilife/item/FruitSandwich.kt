package net.azisaba.vanilife.item

import com.tksimeji.gonunne.item.Food
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object FruitSandwich: Food {
    override val key: Key = Key.key(PLUGIN_ID, "fruit_sandwich")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "fruit_sandwich")

    override val displayName: Component = Component.translatable("item.vanilife.fruit_sandwich")

    override val nutrition: Int = 7

    override val saturation: Float = 5.9f
}