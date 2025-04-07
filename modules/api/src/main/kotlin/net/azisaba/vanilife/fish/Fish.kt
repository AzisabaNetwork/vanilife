package net.azisaba.vanilife.fish

import net.azisaba.vanilife.registry.Keyed
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.noise.SimplexNoiseGenerator

interface Fish: Keyed {
    val lootWeight: Int

    fun tick(player: Player, hook: FishHook, tickNumber: Int, noiseGenerator: SimplexNoiseGenerator)

    fun loot(player: Player, hook: FishHook): List<ItemStack>
}