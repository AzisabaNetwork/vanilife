package net.azisaba.vanilife.extension

import io.papermc.paper.adventure.PaperAdventure
import net.azisaba.vanilife.entity.MobCategory
import net.azisaba.vanilife.util.getRegistry
import net.azisaba.vanilife.world.biome.CustomBiome
import net.azisaba.vanilife.world.biome.ParameterList
import net.kyori.adventure.key.Keyed
import net.kyori.adventure.text.Component
import net.minecraft.core.MappedRegistry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.*
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData
import net.minecraft.world.level.dimension.LevelStem
import org.bukkit.craftbukkit.entity.CraftEntityType
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

fun Component.toMinecraftComponent(): net.minecraft.network.chat.Component {
    return PaperAdventure.asVanilla(this)
}

fun CustomBiome.toMinecraftBiome(): Biome {
    val mobSpawnSettings = MobSpawnSettings.Builder().apply {
        for (spawner in spawners) {
            addSpawn(spawner.mobCategory.toMinecraftMobCategory(), SpawnerData(spawner.entityType.toMinecraftEntityType(), spawner.weight, spawner.count.min, spawner.count.max))
        }
    }.build()

    return Biome.BiomeBuilder()
        .hasPrecipitation(hasPrecipitation)
        .temperature(temperature)
        .temperatureAdjustment(temperatureModifier.toMinecraftTemperatureModifier())
        .downfall(downfall)
        .specialEffects(effects.toMinecraftBiomeSpecialEffect())
        .mobSpawnSettings(mobSpawnSettings)
        .generationSettings(BiomeGenerationSettings.EMPTY)
        .build()
}

fun CustomBiome.Climate.toMinecraftParameterPoint(): Climate.ParameterPoint {
    return Climate.ParameterPoint(
        Climate.Parameter.span(temperature.min, temperature.max),
        Climate.Parameter.span(humidity.min, humidity.max),
        Climate.Parameter.span(continentalness.min, continentalness.max),
        Climate.Parameter.span(erosion.min, erosion.max),
        Climate.Parameter.span(depth.min, depth.max),
        Climate.Parameter.span(weirdness.min, weirdness.max),
        offset
    )
}

fun CustomBiome.Effects.toMinecraftBiomeSpecialEffect(): BiomeSpecialEffects {
    return BiomeSpecialEffects.Builder()
        .fogColor(fogColor.toInt())
        .waterColor(waterColor.toInt())
        .waterFogColor(waterFogColor.toInt())
        .skyColor(skyColor.toInt())
        .backgroundMusicVolume(musicVolume)
        .build()
}

fun CustomBiome.Spawner.toMinecraftSpawnerData(): SpawnerData {
    return SpawnerData(entityType.toMinecraftEntityType(), weight, count.min, count.max)
}

fun CustomBiome.TemperatureModifier.toMinecraftTemperatureModifier(): Biome.TemperatureModifier {
    return when (this) {
        CustomBiome.TemperatureModifier.NONE -> Biome.TemperatureModifier.NONE
        CustomBiome.TemperatureModifier.FROZEN -> Biome.TemperatureModifier.FROZEN
    }
}

fun EntityType.toMinecraftEntityType(): net.minecraft.world.entity.EntityType<*> {
    return CraftEntityType.bukkitToMinecraft(this)
}

fun ItemStack.toMinecraftItemStack(): net.minecraft.world.item.ItemStack {
    return CraftItemStack.asNMSCopy(this)
}

fun Keyed.toMinecraftResourceLocation(): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(key().namespace(), key().value())
}

fun MappedRegistry<*>.frozenSilently() {
    val frozenField = javaClass.getDeclaredField("frozen").apply { isAccessible = true }
    frozenField.set(this, true)
}

fun MappedRegistry<*>.unfrozenSilently() {
    val frozenField = javaClass.getDeclaredField("frozen").apply { isAccessible = true }
    frozenField.set(this, false)
}

fun MobCategory.toMinecraftMobCategory(): net.minecraft.world.entity.MobCategory {
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

fun ParameterList.toMinecraftLevelStem(): LevelStem {
    val levelStemRegistry = getRegistry(Registries.LEVEL_STEM)
    return when (this) {
        ParameterList.OVERWORLD -> levelStemRegistry.getValueOrThrow(LevelStem.OVERWORLD)
        ParameterList.NETHER -> levelStemRegistry.getValueOrThrow(LevelStem.NETHER)
        else -> throw IllegalArgumentException()
    }
}