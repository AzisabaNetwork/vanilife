package net.azisaba.vanilife.runnable

import com.tksimeji.gonunne.fishing.FishingEntry
import net.azisaba.vanilife.runnable.FishingRunnable.Companion.runnable
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.max
import kotlin.random.Random

class FishingBiteAnimationRunnable(private val player: Player, private val hook: FishHook, private val fishingEntry: FishingEntry): BukkitRunnable() {
    private val hookLocation = hook.location.clone()
    private val startLocation = hook.location.clone().add(offset(), 0.0, offset())
    private val controlLocation = Location(hook.world,
        (startLocation.x + hook.x) / 2.0,
        maxOf(startLocation.y, hook.y) + Random.nextDouble(0.5, 1.5),
        (startLocation.z + hook.z) / 2.0)

    private var tick = 0

    override fun run() {
        if (tick >= TOTAL_TICKS || !hook.isValid || hook.x != hookLocation.x || hook.z != hookLocation.z) {
            if (tick >= TOTAL_TICKS) {
                player.playSound(player, Sound.ENTITY_FISHING_BOBBER_SPLASH, 1f, Random.nextDouble(0.8, 1.2).toFloat())
                hook.runnable().fishingEntry = fishingEntry
            }
            cancel()
            return
        }

        val progress = tick.toDouble() / TOTAL_TICKS
        val currentLocation = calculateQuadraticBezier(startLocation, controlLocation, hookLocation, progress)

        val particle = if (hook.runnable().bobbingVehicle == null) Particle.SPLASH else Particle.LAVA

        repeat(5) {
            val offsetX = Random.nextDouble(-0.1, 0.1)
            val offsetZ = Random.nextDouble(-0.1, 0.1)
            hook.world.spawnParticle(particle, currentLocation.clone().add(offsetX, 0.0, offsetZ), 1)
        }

        tick++
    }

    companion object {
        private const val TOTAL_TICKS = 80
        private const val MIN_OFFSET = 5.0
        private const val MAX_OFFSET = 8.0

        private fun offset(): Double {
            return max(Random.nextDouble(-MAX_OFFSET, MAX_OFFSET), MIN_OFFSET)
        }

        private fun calculateQuadraticBezier(startLocation: Location, controlLocation: Location, endLocation: Location, progress: Double): Location {
            val remainingProgress = 1.0 - progress
            val x = remainingProgress * remainingProgress * startLocation.x + 2.0 * remainingProgress * progress * controlLocation.x + progress * progress * endLocation.x
            val y = remainingProgress * remainingProgress * startLocation.y + 2.0 * remainingProgress * progress * controlLocation.y + progress * progress * endLocation.y
            val z = remainingProgress * remainingProgress * startLocation.z + 2.0 * remainingProgress * progress * controlLocation.z + progress * progress * endLocation.z
            return Location(startLocation.world, x, y, z)
        }
    }
}