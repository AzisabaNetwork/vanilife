package com.tksimeji.gonunne.fishing

import com.tksimeji.gonunne.Weighted
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.noise.SimplexNoiseGenerator

interface FishingEntry: Weighted {
    fun tick(player: Player, hook: FishHook, tickNumber: Int, noiseGenerator: SimplexNoiseGenerator)

    fun loot(player: Player, hook: FishHook): List<ItemStack>

    companion object {
        @JvmStatic
        fun runaway(weight: Int, speed: Double, minSpeedNoise: Double, maxSpeedNoise: Double, loot: (Player, FishHook) -> List<ItemStack>): FishingEntry {
            return RunawayFishingEntry(weight, speed, minSpeedNoise, maxSpeedNoise, loot)
        }

        @JvmStatic
        fun vanilla(weight: Int): FishingEntry {
            return VanillaFishingEntry(weight)
        }
    }
}