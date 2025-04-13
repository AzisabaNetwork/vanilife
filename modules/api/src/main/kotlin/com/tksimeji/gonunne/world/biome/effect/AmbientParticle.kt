package com.tksimeji.gonunne.world.biome.effect

import com.tksimeji.gonunne.world.biome.effect.impl.AmbientParticleImpl
import org.bukkit.Particle

interface AmbientParticle {
    fun particle(): Particle

    fun particle(particle: Particle): AmbientParticle

    fun options(): Any?

    fun <D> options(options: D): AmbientParticle

    fun probability(): Float

    fun probability(probability: Float): AmbientParticle

    companion object {
        @JvmStatic
        fun ambientParticle(particle: Particle): AmbientParticle {
            return AmbientParticleImpl(particle)
        }
    }
}