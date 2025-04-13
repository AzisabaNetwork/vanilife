package com.tksimeji.gonunne.world.biome.effect

import com.tksimeji.gonunne.world.biome.effect.impl.MoodSoundImpl
import org.bukkit.Sound

interface MoodSound {
    fun sound(): Sound

    fun sound(sound: Sound): MoodSound

    fun tickDelay(): Int

    fun tickDelay(tickDelay: Int): MoodSound

    fun blockSearchExtent(): Int

    fun blockSearchExtent(blockSearchExtent: Int): MoodSound

    fun offset(): Double

    fun offset(offset: Double): MoodSound

    companion object {
        @JvmStatic
        fun moodSound(sound: Sound): MoodSound {
            return MoodSoundImpl(sound)
        }
    }
}