package net.azisaba.vanilife

import net.azisaba.vanilife.world.biome.CustomBiome
import net.azisaba.vanilife.world.biome.ParameterList
import org.bukkit.generator.BiomeProvider

interface Adapter {
    fun registerCustomBiome(customBiome: CustomBiome)

    fun createBiomeProvider(parameterList: ParameterList, vararg customBiomes: CustomBiome): BiomeProvider
}