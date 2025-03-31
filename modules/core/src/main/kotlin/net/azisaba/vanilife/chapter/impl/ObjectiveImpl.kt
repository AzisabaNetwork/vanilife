package net.azisaba.vanilife.chapter.impl

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.chapter.Objective
import net.kyori.adventure.key.Key
import org.bukkit.OfflinePlayer
import java.util.*

private val DATABASE_CACHE = mutableMapOf<Pair<UUID, Objective>, Boolean>()

abstract class ObjectiveImpl: IObjectiveImpl, Objective {
    final override val key: Key
        get() = Key.key(chapter.key.namespace(), "${chapter.key.value()}/$id")

    override fun achieve(player: OfflinePlayer) {
        if (!chapter.isGranted(player)) {
            throw IllegalArgumentException("${player.name} has not unlocked this chapter.")
        }

        val cacheKey = Pair(player.uniqueId, this)

        if (DATABASE_CACHE[cacheKey] == true || isAchieved(player)) {
            return
        }

        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("INSERT INTO ${Vanilife.DATABASE_PLAYER_OBJECTIVE} VALUES (?, ?)").use { preparedStatement ->
                preparedStatement.setString(1, player.uniqueId.toString())
                preparedStatement.setString(2, key.asString())
                preparedStatement.executeUpdate()
            }
        }

        DATABASE_CACHE[cacheKey] = true
    }

    override fun unachieve(player: OfflinePlayer) {
        val cacheKey = Pair(player.uniqueId, this)

        if (DATABASE_CACHE[cacheKey] == false || !isAchieved(player)) {
            return
        }

        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("DELETE FROM ${Vanilife.DATABASE_PLAYER_OBJECTIVE} WHERE player = ? AND objective = ?").use { preparedStatement ->
                preparedStatement.setString(1, player.uniqueId.toString())
                preparedStatement.setString(2, key.asString())
                preparedStatement.executeUpdate()
            }
        }

        DATABASE_CACHE[cacheKey] = false
    }

    override fun isAchieved(player: OfflinePlayer): Boolean {
        val cacheKey = Pair(player.uniqueId, this)
        val cache = DATABASE_CACHE[cacheKey]

        if (cache != null) {
            return cache
        }

        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("SELECT 1 FROM ${Vanilife.DATABASE_PLAYER_OBJECTIVE} WHERE player = ? AND objective = ? LIMIT 1").use { preparedStatement ->
                preparedStatement.setString(1, player.uniqueId.toString())
                preparedStatement.setString(2, key.asString())
                preparedStatement.executeQuery().use { resultSet ->
                    val exists = resultSet.next()
                    DATABASE_CACHE[cacheKey] = exists
                    return exists
                }
            }
        }
    }
}

private interface IObjectiveImpl {
    val id: String
}