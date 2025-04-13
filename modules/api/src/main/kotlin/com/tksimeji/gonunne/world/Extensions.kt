package com.tksimeji.gonunne.world

import com.tksimeji.gonunne.world.biome.CustomBiome
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import org.bukkit.Location
import org.bukkit.block.Biome
import kotlin.math.sqrt

fun CustomBiome.toPaperBiome(): Biome {
    return RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).getOrThrow(key)
}

fun Location.distance2D(location: Location): Double {
    val deltaX = location.x - x
    val deltaZ = location.z - z
    return sqrt(deltaX * deltaX + deltaZ * deltaZ)
}