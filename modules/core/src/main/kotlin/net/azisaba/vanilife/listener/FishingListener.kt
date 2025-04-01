package net.azisaba.vanilife.listener

import net.azisaba.vanilife.util.runTaskTimer
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.player.PlayerInteractEvent

object FishingListener: Listener {
    @EventHandler
    fun onPlayerFish(event: PlayerFishEvent) {
        val player = event.player

        if (event.state == PlayerFishEvent.State.BITE) {
            val hook = event.hook
            event.isCancelled = true

            runTaskTimer(0, 3) {
                val direction = player.eyeLocation.direction.normalize()
                val oppositeDirection = direction.multiply(1)
                hook.velocity = oppositeDirection.multiply(0.2)
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val hook = player.fishHook

        if (event.material != Material.FISHING_ROD || !event.action.isLeftClick || hook?.isInWater != true) {
            return
        }

        event.isCancelled = true
        hook.state

        val direction = player.eyeLocation.direction.normalize().apply { y = 0.0 }.normalize()
        val oppositeDirection = direction.multiply(1)
        hook.velocity = oppositeDirection.multiply(-0.3)
    }
}