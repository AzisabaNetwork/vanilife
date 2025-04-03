package net.azisaba.vanilife.world.biome

import net.azisaba.vanilife.Range
import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextColor
import org.bukkit.HeightMap
import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import org.bukkit.util.noise.SimplexNoiseGenerator

object SaltLake: CustomBiome {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "salt_lake")

    override val hasPrecipitation: Boolean = false

    override val temperature: Float = 2.0F

    override val downfall: Float = 0.0F

    override val effects: CustomBiome.Effects = CustomBiome.Effects(
        TextColor.color(192, 216, 255),
        TextColor.color(25, 50, 212),
        TextColor.color(5, 5, 51),
        TextColor.color(71, 94, 245)
    )

    override val spawners: List<CustomBiome.Spawner> = emptyList()

    override val climate: CustomBiome.Climate = CustomBiome.Climate(
        Range(0.38F, 1F),
        Range(-1F, -0.12F),
        Range(0.25F, 1F),
        Range(-0.27F, 1F),
        Range(0F, 0F),
        Range(0.11F, 1F),
        0
    )

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