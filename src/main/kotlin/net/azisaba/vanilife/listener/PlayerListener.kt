package net.azisaba.vanilife.listener

import net.azisaba.vanilife.extension.setPlayBoost
import net.azisaba.vanilife.registry.PlayBoosts
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.setPlayBoost(PlayBoosts.CAVENIUM, 2)
    }
}