package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object ButteredPotato: Food {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "buttered_potato")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "buttered_potato")

    override val displayName: Component = Component.translatable("item.vanilife.buttered_potato")

    override val group: ItemGroup = ItemGroups.FOOD

    override val nutrition: Int = 6

    override val saturation: Float = 7F
}