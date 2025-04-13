package com.tksimeji.gonunne.world.biome.spawn

import com.tksimeji.gonunne.MobCategory
import com.tksimeji.gonunne.WeightedList
import com.tksimeji.gonunne.world.biome.spawn.impl.MobSpawnSettingsImpl

interface MobSpawnSettings {
    fun creatureSpawnProbability(): Float

    fun creatureSpawnProbability(creatureSpawnProbability: Float): MobSpawnSettings

    fun spawners(): Map<MobCategory, WeightedList<SpawnerData>>

    fun spawners(vararg spawners: Pair<MobCategory, WeightedList<SpawnerData>>): MobSpawnSettings

    fun spawners(spawners: Map<MobCategory, WeightedList<SpawnerData>>): MobSpawnSettings

    companion object {
        @JvmStatic
        fun mobSpawnSettings(): MobSpawnSettings {
            return MobSpawnSettingsImpl()
        }
    }
}