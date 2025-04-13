package com.tksimeji.gonunne.world.biome.spawn.impl

import com.tksimeji.gonunne.world.biome.spawn.SpawnCost

internal class SpawnCostImpl: SpawnCost {
    private var energyBudget = 0.0

    private var charge = 0.0

    override fun energyBudget(): Double {
        return energyBudget
    }

    override fun energyBudget(energyBudget: Double): SpawnCost {
        this.energyBudget = energyBudget
        return this
    }

    override fun charge(): Double {
        return charge
    }

    override fun charge(charge: Double): SpawnCost {
        this.charge = charge
        return this
    }
}