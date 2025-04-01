package net.azisaba.vanilife.adapter

import net.azisaba.vanilife.biome.CustomBiome

interface Adapter {
    fun registerBiome(customBiome: CustomBiome)
}