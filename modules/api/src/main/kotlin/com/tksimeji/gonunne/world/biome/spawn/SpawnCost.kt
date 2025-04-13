package com.tksimeji.gonunne.world.biome.spawn

import com.tksimeji.gonunne.world.biome.spawn.impl.SpawnCostImpl

interface SpawnCost {
    fun energyBudget(): Double

    fun energyBudget(energyBudget: Double): SpawnCost

    fun charge(): Double

    fun charge(charge: Double): SpawnCost

    companion object {
        @JvmStatic
        fun spawnCost(): SpawnCost {
            return SpawnCostImpl()
        }
    }
}