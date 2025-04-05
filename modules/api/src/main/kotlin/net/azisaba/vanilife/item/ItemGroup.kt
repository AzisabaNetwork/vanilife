package net.azisaba.vanilife.item

import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

interface ItemGroup: Keyed {
    val icon: ItemStack

    val title: Component
}