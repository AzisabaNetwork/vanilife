package com.tksimeji.gonunne.fishing

import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.noise.SimplexNoiseGenerator

abstract class ItemFishingEntry(private val itemStack: ItemStack) : FishingEntry {
    override fun tick(player: Player, hook: FishHook, tickNumber: Int, noiseGenerator: SimplexNoiseGenerator) {
    }

    override fun loot(player: Player, hook: FishHook): List<ItemStack> {
        return listOf(itemStack)
    }
}