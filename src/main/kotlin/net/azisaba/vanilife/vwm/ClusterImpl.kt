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
            connection.prepareStatement("DELETE FROM vwm_cluster WHERE uuid = ?").use { preparedStatement ->
                preparedStatement.setString(1, uuid.toString())
                preparedStatement.executeUpdate()
            }
            connection.prepareStatement("DELETE FROM block WHERE world = ? OR world = ? OR world = ?").use { preparedStatement ->
                preparedStatement.setString(1, overworld.key.asString())
                preparedStatement.setString(2, theNether.key.asString())
                preparedStatement.setString(3, theEnd.key.asString())
                preparedStatement.executeUpdate()
            }
        }
    }

    override fun iterator(): Iterator<World> {
        return worlds.iterator()
    }
}