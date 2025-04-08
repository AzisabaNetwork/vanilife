package net.azisaba.vanilife.chapter

import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

interface Objective: Keyed {
    val icon: ItemStack

    val title: Component

    val hint: String
}