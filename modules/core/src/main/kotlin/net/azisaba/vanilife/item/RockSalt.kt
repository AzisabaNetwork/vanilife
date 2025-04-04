package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object RockSalt: CustomItemType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "rock_salt")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "rock_salt")

    override val displayName: Component = Component.translatable("item.vanilife.rock_salt")

    override val group: ItemGroup = ItemGroups.FOODSTUFF
}