package net.azisaba.vanilife.listener

import com.tksimeji.gonunne.world.distance2D
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.event.FishHookLandEvent
import net.azisaba.vanilife.extensions.customItemType
import net.azisaba.vanilife.extensions.focusHotbarSlot
import net.azisaba.vanilife.extensions.focusItemStack
import net.azisaba.vanilife.extensions.showDialogue
import net.azisaba.vanilife.item.FireproofReelRecipe
import net.azisaba.vanilife.registry.CustomItemTypes
import net.azisaba.vanilife.runnable.FishingHudRunnable
import net.azisaba.vanilife.runnable.FishingRodAnimationRunnable
import net.azisaba.vanilife.runnable.FishingRunnable
import net.azisaba.vanilife.runnable.FishingRunnable.Companion.runnable
import net.azisaba.vanilife.util.runTaskLaterAsync
import net.azisaba.vanilife.util.runTaskTimer
import net.kyori.adventure.text.Component
import org.bukkit.Fluid
import org.bukkit.Sound
import org.bukkit.entity.FishHook
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.world.WorldLoadEvent
import org.bukkit.inventory.meta.Damageable

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
        if (hookLike.location.distance2D(player.location) < 3 && runnable.fishingEntry != null) {
            runnable.caught()
        }

        FishingHudRunnable.click(player)
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

    @EventHandler
    fun onFishHookLand(event: FishHookLandEvent) {
        if (event.fluidData.fluidType != Fluid.LAVA) {
            return
        }

        val hook = event.hook
        val player = event.player
        val fishingRod = event.fishingRod

        runTaskTimer(0, 1) {
            val damageable = fishingRod.itemMeta as Damageable
            damageable.damage += 8
            if (damageable.damage < fishingRod.type.maxDurability) {
                fishingRod.itemMeta = damageable
                return@runTaskTimer true
            }
            fishingRod.amount = 0
            hook.world.playSound(hook.location, Sound.BLOCK_LAVA_EXTINGUISH, 0.7f, 1.0f)

            player.showDialogue(Component.translatable("fishing.fireproofReelRequired")) {
                player.focusItemStack({ itemStack -> itemStack.customItemType() == CustomItemTypes.FIREPROOF_REEL_RECIPE && FireproofReelRecipe.isLocked(itemStack) }) { index ->
                    player.focusHotbarSlot(index, times = 10, task = { count ->
                        if (count % 2 == 0) {
                            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 2.0f)
                        }
                    }) { itemStack ->
                        FireproofReelRecipe.unlock(itemStack!!)
                    }
                }
            }
            return@runTaskTimer false
        }
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
}