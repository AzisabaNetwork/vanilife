package net.azisaba.vanilife.world.biome

import com.tksimeji.gonunne.world.biome.BiomeColor
import com.tksimeji.gonunne.world.biome.CustomBiome
import com.tksimeji.gonunne.world.biome.effect.BiomeEffectSettings
import com.tksimeji.gonunne.world.biome.spawn.MobSpawnSettings
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import org.bukkit.HeightMap
import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import org.bukkit.util.noise.SimplexNoiseGenerator

object SaltLake: CustomBiome {
    override val key: Key = Key.key(PLUGIN_ID, "salt_lake")

    override val hasPrecipitation: Boolean = false

    override val temperature: Float = 2.0F

    override val downfall: Float = 0.0F

    override val biomeEffectSettings: BiomeEffectSettings = BiomeEffectSettings.biomeEffectSettings()
        .fogColor(BiomeColor.biomeColor(192, 216, 255))
        .waterColor(BiomeColor.biomeColor(25, 50, 212))
        .waterFogColor(BiomeColor.biomeColor(5, 5, 51))
        .skyColor(BiomeColor.biomeColor(71, 94, 245))

    override val mobSpawnSettings: MobSpawnSettings = MobSpawnSettings.mobSpawnSettings()

    private const val NOISE_SCALE = 0.1

    override fun generateSurface(chunk: ChunkGenerator.ChunkData, x: Int, y: Int, z: Int, worldX: Int, worldZ: Int, noiseGenerator: SimplexNoiseGenerator, worldInfo: WorldInfo) {
        val noise = noiseGenerator.noise(worldX * NOISE_SCALE, worldZ * NOISE_SCALE)

        val leftHeight = if (x > 0) chunk.getHeight(HeightMap.WORLD_SURFACE_WG, x - 1, z) else y
        val rightHeight = if (x < 15) chunk.getHeight(HeightMap.WORLD_SURFACE_WG, x + 1, z) else y
        val backHeight = if (z > 0) chunk.getHeight(HeightMap.WORLD_SURFACE_WG, x, z - 1) else y
        val frontHeight = if (z < 15) chunk.getHeight(HeightMap.WORLD_SURFACE_WG, x, z + 1) else y

        val isFlat = leftHeight == y && rightHeight == y && backHeight == y && frontHeight == y

        val material = when {
            isFlat && noise < 0.15 -> Material.WATER
            noise < -0.55 -> Material.CLAY
            noise < -0.33 -> Material.SAND
            else -> Material.WHITE_CONCRETE_POWDER
        }

        chunk.setBlock(x, y, z, material)
    }
}