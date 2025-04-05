package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object WheatFlour: CustomItemType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "wheat_flour")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "wheat_flour")

    override val displayName: Component = Component.translatable("item.vanilife.wheat_flour")

    override val group: ItemGroup = ItemGroups.FOODSTUFF
}