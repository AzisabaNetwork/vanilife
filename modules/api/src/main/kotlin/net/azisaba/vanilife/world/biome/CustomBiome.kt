package net.azisaba.vanilife.world.biome

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.azisaba.vanilife.Range
import net.azisaba.vanilife.entity.MobCategory
import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.util.RGBLike
import org.bukkit.block.Biome
import org.bukkit.entity.EntityType
import org.bukkit.generator.ChunkGenerator.ChunkData
import org.bukkit.generator.WorldInfo
import org.bukkit.util.noise.SimplexNoiseGenerator

interface CustomBiome: Keyed {
    val hasPrecipitation: Boolean

    val temperature: Float

    val temperatureModifier: TemperatureModifier
        get() = TemperatureModifier.NONE

    val downfall: Float

    val effects: Effects

    val spawners: List<Spawner>

    val climate: Climate?
        get() = null

    fun generateNoise(chunk: ChunkData, x: Int, y: Int, z: Int, worldX: Int, worldZ: Int, noiseGenerator: SimplexNoiseGenerator, worldInfo: WorldInfo) {
    }

    fun generateSurface(chunk: ChunkData, x: Int, y: Int, z: Int, worldX: Int, worldZ: Int, noiseGenerator: SimplexNoiseGenerator, worldInfo: WorldInfo) {
    }

    fun generateCaves(chunk: ChunkData, x: Int, y: Int, z: Int, worldX: Int, worldZ: Int, noiseGenerator: SimplexNoiseGenerator, worldInfo: WorldInfo) {
    }

    fun toPaperBiome(): Biome = RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).get(key)!!

    data class Climate(
        val temperature: Range<Float>,
        val humidity: Range<Float>,
        val continentalness: Range<Float>,
        val erosion: Range<Float>,
        val depth: Range<Float>,
        val weirdness: Range<Float>,
        val offset: Long = 0
    )

    data class Effects(
        val fogColor: RGBLike,
        val waterColor: RGBLike,
        val waterFogColor: RGBLike,
        val skyColor: RGBLike,
        val musicVolume: Float = 1.0F
    )

    data class Spawner(
        val mobCategory: MobCategory,
        val entityType: EntityType,
        val count: Range<Int>,
        val weight: Int
    )

    enum class TemperatureModifier {
        NONE,
        FROZEN
    }
}