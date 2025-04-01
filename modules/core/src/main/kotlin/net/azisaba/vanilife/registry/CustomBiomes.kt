package net.azisaba.vanilife.registry

import net.azisaba.vanilife.biome.CustomBiome
import net.azisaba.vanilife.biome.SaltLake

object CustomBiomes: KeyedRegistry<CustomBiome>() {
    val SALT_LAKE = register(SaltLake)
}