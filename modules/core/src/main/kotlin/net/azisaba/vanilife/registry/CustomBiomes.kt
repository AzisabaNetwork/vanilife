package net.azisaba.vanilife.registry

import net.azisaba.vanilife.world.biome.CustomBiome
import net.azisaba.vanilife.world.biome.SaltLake

object CustomBiomes: KeyedRegistry<CustomBiome>() {
    val SALT_LAKE = register(SaltLake)
}