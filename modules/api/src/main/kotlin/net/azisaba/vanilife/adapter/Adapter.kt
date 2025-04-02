package net.azisaba.vanilife.adapter

import net.azisaba.vanilife.biome.CustomBiome
import net.azisaba.vanilife.biome.ParameterList
import org.bukkit.generator.BiomeProvider

interface Adapter {
    fun registerCustomBiome(customBiome: CustomBiome)

    fun createBiomeProvider(parameterList: ParameterList, vararg customBiomes: CustomBiome): BiomeProvider
}