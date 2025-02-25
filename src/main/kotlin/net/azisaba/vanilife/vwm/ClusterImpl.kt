package net.azisaba.vanilife.vwm

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.util.getWorldOrCreate
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import java.io.File
import java.time.Year
import java.util.*

internal class ClusterImpl(
    override val uuid: UUID = UUID.randomUUID(),
    override val year: Year,
    override val season: Season,
    override val name: String = "$year-${season.name.lowercase()}",
    override val overworld: World = getWorldOrCreate(Key.key(Vanilife.PLUGIN_ID, "$name/overworld"), environment = World.Environment.NORMAL),
    override val theNether: World = getWorldOrCreate(Key.key(Vanilife.PLUGIN_ID, "$name/the_nether"), environment = World.Environment.NETHER),
    override val theEnd: World = getWorldOrCreate(Key.key(Vanilife.PLUGIN_ID, "$name/the_end"), environment = World.Environment.THE_END)
): Cluster {
    override val worlds: Set<World>
        get() = setOf(overworld, theNether, theEnd)

    override val players: Set<Player>
        get() = worlds.flatMap { it.players }.toSet()

    override fun delete() {
        Cluster.instances.remove(this)
        players.forEach { it.kick() }
        worlds.forEach { Bukkit.unloadWorld(it, true) }
        File(name).deleteRecursively()

        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("DELETE FROM cluster WHERE uuid = ?").use { statement ->
                statement.setString(1, uuid.toString())
                statement.executeUpdate()
            }
        }
    }

    override fun iterator(): Iterator<World> {
        return worlds.iterator()
    }
}