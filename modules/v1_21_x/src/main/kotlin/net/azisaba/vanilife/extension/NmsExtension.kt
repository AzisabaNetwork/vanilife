package net.azisaba.vanilife.extension

import net.azisaba.vanilife.biome.CustomBiome
import net.azisaba.vanilife.entity.MobCategory
import net.kyori.adventure.key.Keyed
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.BiomeGenerationSettings
import net.minecraft.world.level.biome.BiomeSpecialEffects
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData
import org.bukkit.craftbukkit.entity.CraftEntityType
import org.bukkit.entity.EntityType

fun CustomBiome.nms(): Biome {
    val mobSpawnSettings = MobSpawnSettings.Builder().apply {
        for (spawner in spawners) {
            addSpawn(spawner.mobCategory.nms(), SpawnerData(spawner.entityType.nms(), spawner.weight, spawner.minCount, spawner.maxCount))
        }
    }.build()

    return Biome.BiomeBuilder()
        .hasPrecipitation(hasPrecipitation)
        .temperature(temperature)
        .temperatureAdjustment(temperatureModifier.nms())
        .downfall(downfall)
        .specialEffects(effects.nms())
        .mobSpawnSettings(mobSpawnSettings)
        .generationSettings(BiomeGenerationSettings.EMPTY)
        .build()
}

fun CustomBiome.Effects.nms(): BiomeSpecialEffects {
    return BiomeSpecialEffects.Builder()
        .fogColor(fogColor.toInt())
        .waterColor(waterColor.toInt())
        .waterFogColor(waterFogColor.toInt())
        .skyColor(skyColor.toInt())
        .backgroundMusicVolume(musicVolume)
        .build()
}

fun CustomBiome.Spawner.nms(): SpawnerData {
    return SpawnerData(entityType.nms(), weight, minCount, maxCount)
}

fun CustomBiome.TemperatureModifier.nms(): Biome.TemperatureModifier {
    return when (this) {
        CustomBiome.TemperatureModifier.NONE -> Biome.TemperatureModifier.NONE
        CustomBiome.TemperatureModifier.FROZEN -> Biome.TemperatureModifier.FROZEN
    }
}

fun EntityType.nms(): net.minecraft.world.entity.EntityType<*> {
    return CraftEntityType.bukkitToMinecraft(this)
}

fun Keyed.toResourceLocation(): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(key().namespace(), key().value())
}

fun MobCategory.nms(): net.minecraft.world.entity.MobCategory {
    return when (this) {
        MobCategory.MONSTER -> net.minecraft.world.entity.MobCategory.MONSTER
        MobCategory.CREATURE -> net.minecraft.world.entity.MobCategory.CREATURE
        MobCategory.AMBIENT -> net.minecraft.world.entity.MobCategory.AMBIENT
        MobCategory.AXOLOTLS -> net.minecraft.world.entity.MobCategory.AXOLOTLS
        MobCategory.UNDERGROUND_WATER_CREATURE -> net.minecraft.world.entity.MobCategory.UNDERGROUND_WATER_CREATURE
        MobCategory.WATER_CREATURE -> net.minecraft.world.entity.MobCategory.WATER_CREATURE
        MobCategory.WATER_AMBIENT -> net.minecraft.world.entity.MobCategory.WATER_AMBIENT
        MobCategory.MISC -> net.minecraft.world.entity.MobCategory.MISC
    }
}