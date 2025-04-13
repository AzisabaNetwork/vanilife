package com.tksimeji.gonunne.world.biome.effect.impl

import com.tksimeji.gonunne.world.biome.effect.AmbientSound
import org.bukkit.Sound

internal class AmbientSoundImpl(private var sound: Sound): AmbientSound {
    private var range: Float? = null

    override fun sound(): Sound {
        return sound
    }

    override fun sound(sound: Sound): AmbientSound {
        this.sound = sound
        return this
    }

    override fun range(): Float? {
        return range
    }

    override fun range(range: Float?): AmbientSound {
        this.range = range
        return this
    }
}