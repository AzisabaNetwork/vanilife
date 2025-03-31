package net.azisaba.vanilife.chapter

import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.OfflinePlayer

interface Objective: Keyed {
    val title: Component

    val hint: Component

    val chapter: Chapter

    fun achieve(player: OfflinePlayer)

    fun unachieve(player: OfflinePlayer)

    fun isAchieved(player: OfflinePlayer): Boolean
}