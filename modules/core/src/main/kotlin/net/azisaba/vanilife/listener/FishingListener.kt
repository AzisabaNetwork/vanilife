package net.azisaba.vanilife.listener

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.runnable.LavaFishHookRunnable
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.player.PlayerInteractEvent

object FishingListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerFish(event: PlayerFishEvent) {
        val player = event.player
        val fishHook = player.fishHook ?: return
        LavaFishHookRunnable(fishHook).runTaskTimer(Vanilife.plugin, 0, 1)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (!event.action.isLeftClick) {
            return
        }

        val player = event.player
        val fishHook = player.fishHook ?: return

        val bobbleVehicle = LavaFishHookRunnable.instances.firstOrNull { it.fishHook == fishHook && it.bobbingVehicle != null }?.bobbingVehicle

        if (!fishHook.isInWater && bobbleVehicle == null) {
            return
        }

        event.isCancelled = true

        val direction = player.eyeLocation.direction.normalize().apply { y = 0.0 }.normalize()
        val oppositeDirection = direction.multiply(1)

        bobbleVehicle?.velocity = oppositeDirection.multiply(-0.3)
        fishHook.velocity = oppositeDirection.multiply(-0.3)
    }

    /* @EventHandler
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
    } */
}