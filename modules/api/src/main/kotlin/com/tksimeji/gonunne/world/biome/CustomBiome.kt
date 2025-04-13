package com.tksimeji.gonunne.world.biome

import com.tksimeji.gonunne.key.Keyed
import com.tksimeji.gonunne.world.biome.effect.BiomeEffectSettings
import com.tksimeji.gonunne.world.biome.spawn.MobSpawnSettings
import org.bukkit.generator.ChunkGenerator.ChunkData
import org.bukkit.generator.WorldInfo
import org.bukkit.util.noise.SimplexNoiseGenerator

interface CustomBiome: Keyed {
    val hasPrecipitation: Boolean

    val temperature: Float

    val temperatureModifier: TemperatureModifier?
        get() = null

    val downfall: Float

    val biomeEffectSettings: BiomeEffectSettings

    val mobSpawnSettings: MobSpawnSettings

    fun generateNoise(chunk: ChunkData, x: Int, y: Int, z: Int, worldX: Int, worldZ: Int, noiseGenerator: SimplexNoiseGenerator, worldInfo: WorldInfo) {
    }

    fun generateSurface(chunk: ChunkData, x: Int, y: Int, z: Int, worldX: Int, worldZ: Int, noiseGenerator: SimplexNoiseGenerator, worldInfo: WorldInfo) {
    }

    fun generateCaves(chunk: ChunkData, x: Int, y: Int, z: Int, worldX: Int, worldZ: Int, noiseGenerator: SimplexNoiseGenerator, worldInfo: WorldInfo) {
    }
}