package com.tksimeji.gonunne.world.biome.effect.impl

import com.tksimeji.gonunne.world.biome.effect.AdditionsSound
import org.bukkit.Sound

internal class AdditionsSoundImpl(private var sound: Sound): AdditionsSound {
    private var tickChance = 0.0

    override fun sound(): Sound {
        return sound
    }

    override fun sound(sound: Sound): AdditionsSound {
        this.sound = sound
        return this
    }

    override fun tickChance(): Double {
        return tickChance
    }

    override fun tickChance(tickChance: Double): AdditionsSound {
        this.tickChance = tickChance
        return this
    }
}