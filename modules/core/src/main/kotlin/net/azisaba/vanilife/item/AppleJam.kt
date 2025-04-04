package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object AppleJam: CustomItemType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "apple_jam")

    override val itemType: ItemType = ItemType.POTION

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "apple_jam")

    override val displayName: Component = Component.translatable("item.vanilife.apple_jam")

    override val group: ItemGroup = ItemGroups.FOODSTUFF
}