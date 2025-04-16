package com.tksimeji.gonunne.fishing

import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.util.noise.SimplexNoiseGenerator

abstract class RunawayFishingEntry(private val speed: Double, private val minSpeedNoise: Double, private val maxSpeedNoise: Double): FishingEntry {
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
}