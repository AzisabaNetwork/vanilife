package net.azisaba.vanilife.world

import net.azisaba.vanilife.extension.customBiome
import org.bukkit.HeightMap
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import org.bukkit.util.noise.SimplexNoiseGenerator
import java.util.*

open class SimpleChunkGenerator(private val biomeProvider: BiomeProvider): ChunkGenerator() {
    private val noiseGenerators = mutableMapOf<Long, SimplexNoiseGenerator>()

    override fun getDefaultBiomeProvider(worldInfo: WorldInfo): BiomeProvider {
        return biomeProvider
    }

    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        for (x in 0 until 16) {
            for (y in worldInfo.minHeight..worldInfo.maxHeight) {
                for (z in 0 until 16) {
                    val biome = chunkData.getBiome(x, y, z).customBiome ?: continue
                    generate(chunkData, x, y, z, chunkX, chunkZ, worldInfo, biome::generateNoise)
                }
            }
        }
    }

    override fun generateSurface(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        for (x in 0 until 16) {
            for (z in 0 until 16) {
                val height = chunkData.getHeight(HeightMap.WORLD_SURFACE_WG, x, z)
                val biome = chunkData.getBiome(x, height, z).customBiome ?: continue
                generate(chunkData, x, height, z, chunkX, chunkZ, worldInfo, biome::generateSurface)
            }
        }
    }

    override fun generateCaves(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        for (x in 0 until 16) {
            for (y in worldInfo.minHeight..worldInfo.maxHeight) {
                for (z in 0 until 16) {
                    val biome = chunkData.getBiome(x, y, z).customBiome ?: continue
                    generate(chunkData, x, y, z, chunkX, chunkZ, worldInfo, biome::generateCaves)
                }
            }
        }
    }

    override fun shouldGenerateNoise(): Boolean = true

    override fun shouldGenerateSurface(): Boolean = true

    override fun shouldGenerateCaves(): Boolean = true

    override fun shouldGenerateDecorations(): Boolean = true

    override fun shouldGenerateMobs(): Boolean = true

    override fun shouldGenerateStructures(): Boolean = true

    private fun generate(chunk: ChunkData, x: Int, y: Int, z: Int, chunkX: Int, chunkZ: Int, worldInfo: WorldInfo, function: (ChunkData, Int, Int, Int, Int, Int, SimplexNoiseGenerator, WorldInfo) -> Unit) {
        val worldX = chunkX * 16 + x
        val worldZ = chunkZ * 16 + z

        val seed = worldInfo.seed
        val noiseGenerator = noiseGenerators[seed] ?: run {
            SimplexNoiseGenerator(seed).also { noiseGenerators[seed] = it }
        }

        function(chunk, x, y, z, worldX, worldZ, noiseGenerator, worldInfo)
    }
}