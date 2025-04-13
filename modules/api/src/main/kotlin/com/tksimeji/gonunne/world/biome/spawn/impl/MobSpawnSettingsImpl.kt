package com.tksimeji.gonunne.world.biome.spawn.impl

import com.tksimeji.gonunne.MobCategory
import com.tksimeji.gonunne.WeightedList
import com.tksimeji.gonunne.world.biome.spawn.MobSpawnSettings
import com.tksimeji.gonunne.world.biome.spawn.SpawnerData

internal class MobSpawnSettingsImpl: MobSpawnSettings {
    private var creatureSpawnProbability = 0f

    private var spawners = mapOf<MobCategory, WeightedList<SpawnerData>>()

    override fun creatureSpawnProbability(): Float {
        return creatureSpawnProbability
    }

    override fun creatureSpawnProbability(creatureSpawnProbability: Float): MobSpawnSettings {
        this.creatureSpawnProbability = creatureSpawnProbability
        return this
    }

    override fun spawners(): Map<MobCategory, WeightedList<SpawnerData>> {
        return spawners
    }

    override fun spawners(vararg spawners: Pair<MobCategory, WeightedList<SpawnerData>>): MobSpawnSettings {
        return spawners(spawners.toMap())
    }

    override fun spawners(spawners: Map<MobCategory, WeightedList<SpawnerData>>): MobSpawnSettings {
        this.spawners = spawners
        return this
    }
}