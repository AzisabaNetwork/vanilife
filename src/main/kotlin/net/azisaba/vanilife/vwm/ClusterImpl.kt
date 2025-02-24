package net.azisaba.vanilife.vwm

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.util.getWorldOrCreate
import net.kyori.adventure.key.Key
import org.bukkit.World
import org.bukkit.entity.Player
import java.time.Year

internal class ClusterImpl(
    override val year: Year,
    override val season: Season,
    override val overworld: World = getWorldOrCreate(Key.key(Vanilife.PLUGIN_ID, "$year-${season.name.lowercase()}/overworld"), environment = World.Environment.NORMAL),
    override val theNether: World = getWorldOrCreate(Key.key(Vanilife.PLUGIN_ID, "$year-${season.name.lowercase()}/the_nether"), environment = World.Environment.NETHER),
    override val theEnd: World = getWorldOrCreate(Key.key(Vanilife.PLUGIN_ID, "$year-${season.name.lowercase()}/the_end"), environment = World.Environment.THE_END)
) : Cluster {
    override val worlds: Set<World>
        get() = setOf(overworld, theNether, theEnd)

    override val players: Set<Player>
        get() = worlds.flatMap { it.players }.toSet()

    override fun iterator(): Iterator<World> {
        return worlds.iterator()
    }
}