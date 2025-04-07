package net.azisaba.vanilife.fish

import net.kyori.adventure.key.Key
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.noise.SimplexNoiseGenerator

open class SimpleFish(
    override val key: Key,
    override val lootWeight: Int,
    private val item: ItemStack,
    private val speed: Double,
    private val minSpeedNoise: Double,
    private val maxSpeedNoise: Double
): Fish {
    private val noiseScaleFactor = maxSpeedNoise - minSpeedNoise

    override fun tick(player: Player, hook: FishHook, tickNumber: Int, noiseGenerator: SimplexNoiseGenerator) {
        val playerLocation = player.location
        val hookLocation = hook.location

        val direction = hookLocation.toVector().subtract(playerLocation.toVector()).normalize()

        if (direction.lengthSquared() == 0.0) {
            return
        }

        val speedNoise = (noiseGenerator.noise(tickNumber.toDouble()) * noiseScaleFactor).coerceIn(minSpeedNoise, maxSpeedNoise)
        val additionalVelocity = direction.multiply(speed * speedNoise)

        hook.velocity = hook.velocity.add(additionalVelocity)
    }

    override fun loot(player: Player, hook: FishHook): List<ItemStack> {
        return listOf(item)
    }
}