package net.azisaba.vanilife.chapter

import net.azisaba.vanilife.registry.IKeyedRegistry
import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

interface Chapter: IKeyedRegistry<Objective>, Keyed {
    val icon: ItemStack

    val title: Component

    val description: List<Component>

    val difficulty: Difficulty

    val objectives: List<Objective>
        get() = values

    fun grant(player: OfflinePlayer)

    fun revoke(player: OfflinePlayer)

    fun isGranted(player: OfflinePlayer): Boolean
}