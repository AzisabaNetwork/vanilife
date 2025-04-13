package com.tksimeji.gonunne.world.biome.effect

import com.tksimeji.gonunne.WeightedList
import com.tksimeji.gonunne.sound.Music
import com.tksimeji.gonunne.world.biome.BiomeColor
import com.tksimeji.gonunne.world.biome.effect.impl.BiomeEffectSettingsImpl

interface BiomeEffectSettings {
    fun fogColor(): BiomeColor

    fun fogColor(fogColor: BiomeColor): BiomeEffectSettings

    fun waterColor(): BiomeColor

    fun waterColor(waterColor: BiomeColor): BiomeEffectSettings

    fun waterFogColor(): BiomeColor

    fun waterFogColor(waterFogColor: BiomeColor): BiomeEffectSettings

    fun skyColor(): BiomeColor

    fun skyColor(skyColor: BiomeColor): BiomeEffectSettings

    fun foliageColor(): BiomeColor?

    fun foliageColor(foliageColor: BiomeColor?): BiomeEffectSettings

    fun grassColor(): BiomeColor?

    fun grassColor(grassColor: BiomeColor?): BiomeEffectSettings

    fun grassColorModifier(): GrassColorModifier?

    fun grassColorModifier(grassColorModifier: GrassColorModifier?): BiomeEffectSettings

    fun ambientParticle(): AmbientParticle?

    fun ambientParticle(ambientParticle: AmbientParticle?): BiomeEffectSettings

    fun ambientSound(): AmbientSound?

    fun ambientSound(ambientSound: AmbientSound?): BiomeEffectSettings

    fun moodSound(): MoodSound?

    fun moodSound(moodSound: MoodSound?): BiomeEffectSettings

    fun additionsSound(): AdditionsSound?

    fun additionsSound(additionsSound: AdditionsSound?): BiomeEffectSettings

    fun music(): WeightedList<Music>?

    fun music(music: WeightedList<Music>?): BiomeEffectSettings

    fun musicVolume(): Float

    fun musicVolume(musicVolume: Float): BiomeEffectSettings

    companion object {
        @JvmStatic
        fun biomeEffectSettings(): BiomeEffectSettings {
            return BiomeEffectSettingsImpl()
        }
    }

    enum class GrassColorModifier {
        NONE,
        DARK_FOREST,
        SWAMP
    }
}