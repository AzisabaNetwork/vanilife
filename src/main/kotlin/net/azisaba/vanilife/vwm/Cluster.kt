package net.azisaba.vanilife.vwm

import org.bukkit.World
import org.bukkit.entity.Player
import java.time.Year

interface Cluster: Iterable<World> {
    companion object {
        private val instances: MutableSet<Cluster> = mutableSetOf()

        fun get(year: Year = Year.now(), season: Season): Cluster? {
            return instances.firstOrNull { it.year == year && it.season == season }
        }

        fun all(): Set<Cluster> {
            return instances.toSet()
        }

        fun create(year: Year, season: Season): Cluster {
            get(year, season)?.let { return it }
            return ClusterImpl(year, season).also { instances.add(it) }
        }
    }

    val year: Year

    val season: Season

    val overworld: World

    val theNether: World

    val theEnd: World

    val worlds: Set<World>

    val players: Set<Player>
}