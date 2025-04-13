package com.tksimeji.gonunne.world.biome.spawn

import com.tksimeji.gonunne.Range
import com.tksimeji.gonunne.world.biome.spawn.impl.SpawnerDataImpl
import org.bukkit.entity.EntityType

interface SpawnerData {
    fun type(): EntityType

    fun type(type: EntityType): SpawnerData

    fun count(): Range<Int>

    fun count(min: Int, max: Int): SpawnerData

    fun minCount(): Int

    fun minCount(minCount: Int): SpawnerData

    fun maxCount(): Int

    fun maxCount(maxCount: Int): SpawnerData

    companion object {
        @JvmStatic
        fun spawnerData(type: EntityType): SpawnerData {
            return SpawnerDataImpl(type)
        }
    }
}