package com.tksimeji.gonunne.world.biome.effect.impl

import com.tksimeji.gonunne.world.biome.effect.MoodSound
import org.bukkit.Sound

internal class MoodSoundImpl(private var sound: Sound): MoodSound {
    private var tickDelay = 0

    private var blockSearchExtent = 0

    private var offset = 0.0

    override fun sound(): Sound {
        return sound
    }

    override fun sound(sound: Sound): MoodSound {
        this.sound = sound
        return this
    }

    override fun tickDelay(): Int {
        return tickDelay
    }

    override fun tickDelay(tickDelay: Int): MoodSound {
        this.tickDelay = tickDelay
        return this
    }

    override fun blockSearchExtent(): Int {
        return blockSearchExtent
    }

    override fun blockSearchExtent(blockSearchExtent: Int): MoodSound {
        this.blockSearchExtent = blockSearchExtent
        return this
    }

    override fun offset(): Double {
        return offset
    }

    override fun offset(offset: Double): MoodSound {
        this.offset = offset
        return this
    }
}