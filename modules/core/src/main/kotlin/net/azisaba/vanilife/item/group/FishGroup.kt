package net.azisaba.vanilife.item.group

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.createItemStack
import net.azisaba.vanilife.item.ItemGroup
import net.azisaba.vanilife.registry.CustomItemTypes
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

object FishGroup: ItemGroup {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "fish")

    override val icon: ItemStack by lazy { CustomItemTypes.CRUCIAN_CARP.createItemStack() }

    override val title: Component = Component.translatable("itemGroup.vanilife.fish")
}