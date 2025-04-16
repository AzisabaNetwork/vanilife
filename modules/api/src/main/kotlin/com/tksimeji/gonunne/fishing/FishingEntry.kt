package com.tksimeji.gonunne.fishing

import com.tksimeji.gonunne.Weighted
import com.tksimeji.gonunne.key.Keyed
import org.bukkit.Location
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.noise.SimplexNoiseGenerator

interface FishingEntry: Keyed, Weighted {
    fun condition(player: Player, location: Location): Boolean

    fun tick(player: Player, hook: FishHook, tickNumber: Int, noiseGenerator: SimplexNoiseGenerator)

    fun loot(player: Player, hook: FishHook): List<ItemStack>

    companion object {
        @JvmStatic
        fun vanilla(weight: Int): FishingEntry {
            return VanillaFishingEntry(weight)
        }
    }
}