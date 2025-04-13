package net.azisaba.vanilife.extensions

import com.tksimeji.gonunne.MobCategory
import com.tksimeji.gonunne.WeightedList
import com.tksimeji.gonunne.sound.Music
import com.tksimeji.gonunne.world.ParameterPoint
import com.tksimeji.gonunne.world.biome.CustomBiome
import com.tksimeji.gonunne.world.biome.TemperatureModifier
import com.tksimeji.gonunne.world.biome.effect.*
import com.tksimeji.gonunne.world.biome.spawn.MobSpawnSettings
import io.papermc.paper.adventure.PaperAdventure
import net.kyori.adventure.key.Keyed
import net.kyori.adventure.text.Component
import net.minecraft.core.Holder
import net.minecraft.core.MappedRegistry
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.util.random.SimpleWeightedRandomList
import net.minecraft.world.level.biome.*
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData
import org.bukkit.Registry
import org.bukkit.Sound
import org.bukkit.craftbukkit.CraftParticle
import org.bukkit.craftbukkit.CraftSound
import org.bukkit.craftbukkit.entity.CraftEntityType
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import java.util.*

fun AdditionsSound.toMinecraftAmbientAdditionsSettings(): AmbientAdditionsSettings {
    return AmbientAdditionsSettings(Holder.direct(sound().toMinecraftSoundEvent()), tickChance())
}

fun AmbientParticle.toMinecraftAmbientParticleSettings(): AmbientParticleSettings {
    return AmbientParticleSettings(CraftParticle.createParticleParam(particle(), options()), probability())
}

fun AmbientSound.toMinecraftSoundEvent(): SoundEvent {
    val sound = Registry.SOUND_EVENT.getKeyOrThrow(sound()).toMinecraftResourceLocation()
    return SoundEvent(sound, Optional.ofNullable(range()))
}

fun BiomeEffectSettings.toMinecraftBiomeSpecialEffects(): BiomeSpecialEffects {
    val builder = BiomeSpecialEffects.Builder()
        .fogColor(fogColor().toInt())
        .waterColor(waterColor().toInt())
        .waterFogColor(waterFogColor().toInt())
        .skyColor(skyColor().toInt())
        .backgroundMusicVolume(musicVolume())

    foliageColor()?.let { builder.foliageColorOverride(it.toInt()) }
    grassColor()?.let { builder.grassColorOverride(it.toInt()) }
    grassColorModifier()?.let { builder.grassColorModifier(it.toMinecraftGrassColorModifier()) }
    ambientParticle()?.let { builder.ambientParticle(it.toMinecraftAmbientParticleSettings()) }
    ambientSound()?.let { builder.ambientLoopSound(Holder.direct(it.toMinecraftSoundEvent())) }
    moodSound()?.let { builder.ambientMoodSound(it.toMinecraftAmbientMoodSettings()) }
    additionsSound()?.let { builder.ambientAdditionsSound(it.toMinecraftAmbientAdditionsSettings()) }
    music()?.let { builder.backgroundMusic(it.toMinecraftWeightedRandomList()) }

    return builder.build()
}

fun BiomeEffectSettings.GrassColorModifier.toMinecraftGrassColorModifier(): GrassColorModifier {
    return when (this) {
        BiomeEffectSettings.GrassColorModifier.NONE -> GrassColorModifier.NONE
        BiomeEffectSettings.GrassColorModifier.DARK_FOREST -> GrassColorModifier.DARK_FOREST
        BiomeEffectSettings.GrassColorModifier.SWAMP -> GrassColorModifier.SWAMP
    }
}

fun Component.toMinecraftComponent(): net.minecraft.network.chat.Component {
    return PaperAdventure.asVanilla(this)
}

fun CustomBiome.toMinecraftBiome(): Biome {
    return Biome.BiomeBuilder()
        .hasPrecipitation(hasPrecipitation)
        .temperature(temperature)
        .temperatureAdjustment(temperatureModifier?.toMinecraftTemperatureModifier() ?: Biome.TemperatureModifier.NONE)
        .downfall(downfall)
        .specialEffects(biomeEffectSettings.toMinecraftBiomeSpecialEffects())
        .mobSpawnSettings(mobSpawnSettings.toMinecraftMobSpawnSettings())
        .generationSettings(BiomeGenerationSettings.EMPTY)
        .build()
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

fun MobSpawnSettings.toMinecraftMobSpawnSettings(): net.minecraft.world.level.biome.MobSpawnSettings {
    val builder = net.minecraft.world.level.biome.MobSpawnSettings.Builder()
    builder.creatureGenerationProbability(creatureSpawnProbability())

    for ((mobCategory, weightedList) in spawners()) {
        for ((spawnerData, weight) in weightedList.toMap()) {
            builder.addSpawn(mobCategory.toMinecraftMobCategory(), SpawnerData(spawnerData.type().toMinecraftEntityType(), weight, spawnerData.minCount(), spawnerData.maxCount()))
        }
    }

    return builder.build()
}

fun MoodSound.toMinecraftAmbientMoodSettings(): AmbientMoodSettings {
    return AmbientMoodSettings(Holder.direct(sound().toMinecraftSoundEvent()), tickDelay(), blockSearchExtent(), offset())
}

fun Music.toMinecraftMusic(): net.minecraft.sounds.Music {
    return net.minecraft.sounds.Music(Holder.direct(sound.toMinecraftSoundEvent()), minDelay, maxDelay, replaceCurrentMusic)
}

fun ParameterPoint.toMinecraftParameterPoint(): Climate.ParameterPoint {
    return Climate.ParameterPoint(
        Climate.Parameter.span(temperature().min, temperature().max),
        Climate.Parameter.span(humidity().min, humidity().max),
        Climate.Parameter.span(continentalness().min, continentalness().max),
        Climate.Parameter.span(erosion().min, erosion().max),
        Climate.Parameter.span(depth().min, depth().max),
        Climate.Parameter.span(weirdness().min, weirdness().max),
        offset()
    )
}

fun Sound.toMinecraftSoundEvent(): SoundEvent {
    return CraftSound.bukkitToMinecraft(this)
}

fun TemperatureModifier.toMinecraftTemperatureModifier(): Biome.TemperatureModifier {
    return when (this) {
        TemperatureModifier.NONE -> Biome.TemperatureModifier.NONE
        TemperatureModifier.FROZEN -> Biome.TemperatureModifier.FROZEN
    }
}

fun WeightedList<Music>.toMinecraftWeightedRandomList(): SimpleWeightedRandomList<net.minecraft.sounds.Music> {
    val builder = SimpleWeightedRandomList.builder<net.minecraft.sounds.Music>()
    for (music in this) {
        builder.add(music.toMinecraftMusic())
    }
    return builder.build()
}