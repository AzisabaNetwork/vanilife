package com.tksimeji.gonunne.world.biome.effect

import com.tksimeji.gonunne.world.biome.effect.impl.AdditionsSoundImpl
import org.bukkit.Sound

interface AdditionsSound {
    fun sound(): Sound

    fun sound(sound: Sound): AdditionsSound

    fun tickChance(): Double

    fun tickChance(tickChance: Double): AdditionsSound

    companion object {
        @JvmStatic
        fun additionsSound(sound: Sound): AdditionsSound {
            return AdditionsSoundImpl(sound)
        }
    }
}