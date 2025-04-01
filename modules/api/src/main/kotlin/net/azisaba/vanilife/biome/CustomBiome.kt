package net.azisaba.vanilife.biome

import net.azisaba.vanilife.entity.MobCategory
import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.util.RGBLike
import org.bukkit.entity.EntityType

interface CustomBiome: Keyed {
    val hasPrecipitation: Boolean

    val temperature: Float

    val temperatureModifier: TemperatureModifier
        get() = TemperatureModifier.NONE

    val downfall: Float

    val effects: Effects

    val spawners: List<Spawner>

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
        val minCount: Int,
        val maxCount: Int,
        val weight: Int
    )

    enum class TemperatureModifier {
        NONE,
        FROZEN
    }
}