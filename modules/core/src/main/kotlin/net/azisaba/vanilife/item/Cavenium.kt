package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemType

object Cavenium: CustomItemType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "cavenium")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "cavenium")

    override val displayName: Component = Component.translatable("item.vanilife.cavenium")

    override val group: ItemGroup = ItemGroups.MATERIAL

    override val rarity: ItemRarity = ItemRarity.UNCOMMON
}