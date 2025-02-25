package net.azisaba.vanilife.vwm

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.toKey
import net.azisaba.vanilife.extension.toUuid
import net.azisaba.vanilife.util.getWorldOrCreate
import net.kyori.adventure.key.Key
import org.bukkit.World
import org.bukkit.entity.Player
import java.time.Year
import java.util.UUID

interface Cluster: Iterable<World> {
    companion object {
        internal val instances: MutableSet<Cluster> = mutableSetOf()

        fun get(uuid: UUID): Cluster? {
            return instances.firstOrNull { it.uuid == uuid }
        }

        fun get(name: String): Cluster? {
            return instances.firstOrNull { it.name == name }
        }

        fun get(year: Year = Year.now(), season: Season): Cluster? {
            return instances.firstOrNull { it.year == year && it.season == season }
        }

        fun all(): Set<Cluster> {
            return instances.toSet()
        }

        fun init() {
            Vanilife.dataSource.connection.use { connection ->
                connection.prepareStatement("SELECT * FROM cluster").use { statement ->
                    statement.executeQuery().use { resultSet ->
                        while (resultSet.next()) {
                            create(resultSet.getString("uuid").toUuid(),
                                Year.of(resultSet.getInt("year")),
                                Season.valueOf(resultSet.getString("season").uppercase()),
                                resultSet.getString("overworld").toKey(),
                                resultSet.getString("the_nether").toKey(),
                                resultSet.getString("the_end").toKey())
                        }
                    }
                }
            }
        }

        fun create(year: Year, season: Season): Cluster {
            get(year, season)?.let { return it }
            return ClusterImpl(year = year, season = season).also {
                instances.add(it)

                Vanilife.dataSource.connection.use { connection ->
                    connection.prepareStatement("INSERT INTO cluster VALUES(?, ?, ?, ?, ?, ?)").use { statement ->
                        statement.setString(1, it.uuid.toString())
                        statement.setInt(2, year.value)
                        statement.setString(3, season.name.lowercase())
                        statement.setString(4, it.overworld.key.asString())
                        statement.setString(5, it.theNether.key.asString())
                        statement.setString(6, it.theEnd.key.asString())
                        statement.executeUpdate()
                    }
                }
            }
        }

        private fun create(uuid: UUID, year: Year, season: Season, overworld: Key, theNether: Key, theEnd: Key): Cluster {
            get(year, season)?.let { return it }
            return ClusterImpl(
                uuid,
                year,
                season,
                overworld = getWorldOrCreate(overworld, environment = World.Environment.NORMAL),
                theNether =  getWorldOrCreate(theNether, environment = World.Environment.NETHER),
                theEnd = getWorldOrCreate(theEnd, environment = World.Environment.THE_END)
            ).also { instances.add(it) }
        }
    }

    val uuid: UUID

    val name: String

    val year: Year

    val season: Season

    val overworld: World

    val theNether: World

    val theEnd: World

    val worlds: Set<World>

    val players: Set<Player>

    fun delete()
}