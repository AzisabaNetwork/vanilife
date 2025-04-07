package net.azisaba.vanilife.listener

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.distance2D
import net.azisaba.vanilife.runnable.FishingHudRunnable
import net.azisaba.vanilife.runnable.FishingRodAnimationRunnable
import net.azisaba.vanilife.runnable.FishingRunnable
import net.azisaba.vanilife.runnable.FishingRunnable.Companion.runnable
import net.azisaba.vanilife.util.runTaskLaterAsync
import org.bukkit.Sound
import org.bukkit.entity.FishHook
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.world.WorldLoadEvent

object FishingListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerFish(event: PlayerFishEvent) {
        val player = event.player
        val fishHook = player.fishHook ?: return

        if (event.state == PlayerFishEvent.State.FISHING) {
            FishingRunnable(player, fishHook).runTaskTimer(Vanilife.plugin, 0, 1)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (!event.action.isLeftClick) {
            return
        }

        val player = event.player
        val fishHook = player.fishHook ?: return

        val runnable = fishHook.runnable()
        val bobbleVehicle = runnable.bobbingVehicle

        if (!fishHook.isInWater && bobbleVehicle == null) {
            return
        }

        event.isCancelled = true

        val direction = player.eyeLocation.direction.normalize().apply { y = 0.0 }.normalize()
        val oppositeDirection = direction.multiply(1)

        val hookLike = runnable.entity
        hookLike.velocity = oppositeDirection.multiply(-0.3)
        if (hookLike.location.distance2D(player.location) < 3 && runnable.fish != null) {
            runnable.caught()
        }

        FishingHudRunnable.click(player)
    }

    @EventHandler
    fun onWorldLoad(event: WorldLoadEvent) {
        val world = event.world
        for (entity in world.entities) {
            if (entity is FishHook && entity.hasMetadata(FishingRunnable.BOBBING_VEHICLE_METADATA)) {
                entity.remove()
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerItemHeld(event: PlayerItemHeldEvent) {
        val player = event.player
        runTaskLaterAsync(10) {
            if (FishingRodAnimationRunnable.isAnimationNeeded(player) && FishingRodAnimationRunnable.instances.none { it.player == player }) {
                FishingRodAnimationRunnable(player).runTaskTimerAsynchronously(Vanilife.plugin, 0, 1)
            }
        }
    }
}