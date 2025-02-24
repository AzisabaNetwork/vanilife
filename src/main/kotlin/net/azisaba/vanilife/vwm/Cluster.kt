package net.azisaba.vanilife.vwm

import org.bukkit.World
import org.bukkit.entity.Player

interface Cluster: Iterable<World> {
    val year: Int

    val season: Season

    val overworld: World

    val theNether: World

    val theEnd: World

    val worlds: Set<World>
        get() = setOf(overworld, theNether, theEnd)

    val players: Set<Player>
        get() = worlds.flatMap { it.players }.toSet()

    override fun iterator(): Iterator<World> {
        return worlds.iterator()
    }
}