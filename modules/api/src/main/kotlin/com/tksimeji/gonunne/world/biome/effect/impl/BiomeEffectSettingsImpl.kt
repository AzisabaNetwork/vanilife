package com.tksimeji.gonunne.world.biome.effect.impl

import com.tksimeji.gonunne.WeightedList
import com.tksimeji.gonunne.sound.Music
import com.tksimeji.gonunne.world.biome.BiomeColor
import com.tksimeji.gonunne.world.biome.effect.*

internal class BiomeEffectSettingsImpl: BiomeEffectSettings {
    private var fogColor = BiomeColor.biomeColor(192, 216, 255)

    private var waterColor = BiomeColor.biomeColor(63, 118, 228)

    private var waterFogColor = BiomeColor.biomeColor(5, 5, 51)

    private var skyColor = BiomeColor.biomeColor(120, 167, 255)

    private var foliageColor: BiomeColor? = null

    private var grassColor: BiomeColor? = null

    private var grassColorModifier: BiomeEffectSettings.GrassColorModifier? = null

    private var ambientParticle: AmbientParticle? = null

    private var ambientSound: AmbientSound? = null

    private var moodSound: MoodSound? = null

    private var additionsSound: AdditionsSound? = null

    private var music: WeightedList<Music>? = null

    private var musicVolume: Float = 1f

    override fun fogColor(): BiomeColor {
        return fogColor
    }

    override fun fogColor(fogColor: BiomeColor): BiomeEffectSettings {
        this.fogColor = fogColor
        return this
    }

    override fun waterColor(): BiomeColor {
        return waterColor
    }

    override fun waterColor(waterColor: BiomeColor): BiomeEffectSettings {
        this.waterColor = waterColor
        return this
    }

    override fun waterFogColor(): BiomeColor {
        return waterFogColor
    }

    override fun waterFogColor(waterFogColor: BiomeColor): BiomeEffectSettings {
        this.waterFogColor = waterFogColor
        return this
    }

    override fun skyColor(): BiomeColor {
        return skyColor
    }

    override fun skyColor(skyColor: BiomeColor): BiomeEffectSettings {
        this.skyColor = skyColor
        return this
    }

    override fun foliageColor(): BiomeColor? {
        return foliageColor
    }

    override fun foliageColor(foliageColor: BiomeColor?): BiomeEffectSettings {
        this.foliageColor = foliageColor
        return this
    }

    override fun grassColor(): BiomeColor? {
        return grassColor
    }

    override fun grassColor(grassColor: BiomeColor?): BiomeEffectSettings {
        this.grassColor = grassColor
        return this
    }

    override fun grassColorModifier(): BiomeEffectSettings.GrassColorModifier? {
        return grassColorModifier
    }

    override fun grassColorModifier(grassColorModifier: BiomeEffectSettings.GrassColorModifier?): BiomeEffectSettings {
        this.grassColorModifier = grassColorModifier
        return this
    }

    override fun ambientParticle(): AmbientParticle? {
        return ambientParticle
    }

    override fun ambientParticle(ambientParticle: AmbientParticle?): BiomeEffectSettings {
        this.ambientSound = ambientSound
        return this
    }

    override fun ambientSound(): AmbientSound? {
        return ambientSound
    }

    override fun ambientSound(ambientSound: AmbientSound?): BiomeEffectSettings {
        this.ambientSound = ambientSound
        return this
    }

    override fun moodSound(): MoodSound? {
        return moodSound
    }

    override fun moodSound(moodSound: MoodSound?): BiomeEffectSettings {
        this.moodSound = moodSound
        return this
    }

    override fun additionsSound(): AdditionsSound? {
        return additionsSound
    }

    override fun additionsSound(additionsSound: AdditionsSound?): BiomeEffectSettings {
        this.additionsSound = additionsSound
        return this
    }

    override fun music(): WeightedList<Music>? {
        return music
    }

    override fun music(music: WeightedList<Music>?): BiomeEffectSettings {
        this.music = music
        return this
    }

    override fun musicVolume(): Float {
        return musicVolume
    }

    override fun musicVolume(musicVolume: Float): BiomeEffectSettings {
        this.musicVolume = musicVolume
        return this
    }
}