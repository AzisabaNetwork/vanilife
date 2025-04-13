package com.tksimeji.gonunne.world.biome.effect.impl

import com.tksimeji.gonunne.world.biome.effect.AmbientParticle
import org.bukkit.Particle

internal class AmbientParticleImpl(private var particle: Particle): AmbientParticle {
    private var options: Any? = null

    private var probability = 0f

    override fun particle(): Particle {
        return particle
    }

    override fun particle(particle: Particle): AmbientParticle {
        this.particle = particle
        return this
    }

    override fun options(): Any? {
        return options
    }

    override fun <D> options(options: D): AmbientParticle {
        this.options = options
        return this
    }

    override fun probability(): Float {
        return probability
    }

    override fun probability(probability: Float): AmbientParticle {
        this.probability = probability
        return this
    }
}