package com.tksimeji.gonunne

import com.tksimeji.gonunne.world.ParameterPoint
import com.tksimeji.gonunne.world.biome.CustomBiome
import io.papermc.paper.potion.PotionMix
import net.kyori.adventure.key.Keyed
import org.bukkit.generator.BiomeProvider

interface Adapter {
    val version: Set<String>

    fun registerCustomBiome(customBiome: CustomBiome)

    fun registerPotionMixes(potionMixes: Collection<PotionMix>)

    fun createBiomeProvider(vararg biomes: Pair<ParameterPoint, Keyed>): BiomeProvider

    fun createOverworldBiomeProvider(vararg biomes: Pair<ParameterPoint, Keyed>): BiomeProvider

    fun createNetherBiomeProvider(vararg biomes: Pair<ParameterPoint, Keyed>): BiomeProvider
}