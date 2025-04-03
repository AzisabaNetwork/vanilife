package net.azisaba.vanilife.extension

import net.azisaba.vanilife.registry.CustomBiomes
import net.azisaba.vanilife.world.biome.CustomBiome
import org.bukkit.block.Biome

val Biome.customBiome: CustomBiome?
    get() = CustomBiomes.get(key)

fun Biome.hasCustomBiome(): Boolean {
    return customBiome != null
}