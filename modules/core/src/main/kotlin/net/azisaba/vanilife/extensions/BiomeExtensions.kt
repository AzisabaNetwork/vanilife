package net.azisaba.vanilife.extensions

import com.tksimeji.gonunne.world.biome.CustomBiome
import net.azisaba.vanilife.registry.CustomBiomes
import org.bukkit.block.Biome

fun Biome.customBiome(): CustomBiome? {
    return CustomBiomes.get(key)
}

fun Biome.hasCustomBiome(): Boolean {
    return customBiome() != null
}