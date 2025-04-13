package com.tksimeji.gonunne.world.biome.effect

import com.tksimeji.gonunne.world.biome.effect.impl.AmbientSoundImpl
import org.bukkit.Sound

interface AmbientSound {
    fun sound(): Sound

    fun sound(sound: Sound): AmbientSound

    fun range(): Float?

    fun range(range: Float?): AmbientSound

    companion object {
        @JvmStatic
        fun ambientSound(sound: Sound): AmbientSound {
            return AmbientSoundImpl(sound)
        }
    }
}