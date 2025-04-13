package com.tksimeji.gonunne.world.biome.spawn.impl

import com.tksimeji.gonunne.Range
import com.tksimeji.gonunne.world.biome.spawn.SpawnerData
import org.bukkit.entity.EntityType

internal class SpawnerDataImpl(private var type: EntityType): SpawnerData {
    private var minCount = 0

    private var maxCount = 0

    override fun type(): EntityType {
        return type
    }

    override fun type(type: EntityType): SpawnerData {
        this.type = type
        return this
    }

    override fun count(): Range<Int> {
        return Range(minCount, maxCount)
    }

    override fun count(min: Int, max: Int): SpawnerData {
        minCount(min)
        maxCount(max)
        return this
    }

    override fun minCount(): Int {
        return minCount
    }

    override fun minCount(minCount: Int): SpawnerData {
        this.minCount = minCount
        return this
    }

    override fun maxCount(): Int {
        return maxCount
    }

    override fun maxCount(maxCount: Int): SpawnerData {
        this.maxCount = maxCount
        return this
    }
}