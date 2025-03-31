package net.azisaba.vanilife.chapter.impl

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.chapter.Chapter
import net.azisaba.vanilife.chapter.Objective
import net.azisaba.vanilife.registry.KeyedRegistry
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*

private val DATABASE_CACHE = mutableMapOf<Pair<UUID, Chapter>, Boolean>()

abstract class ChapterImpl: KeyedRegistry<Objective>(), Chapter {
    override fun grant(player: OfflinePlayer) {
        val cacheKey = Pair(player.uniqueId, this)

        if (DATABASE_CACHE[cacheKey] == true || isGranted(player)) {
            return
        }

        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("INSERT INTO ${Vanilife.DATABASE_PLAYER_CHAPTER} VALUES (?, ?)").use { preparedStatement ->
                preparedStatement.setString(1, player.uniqueId.toString())
                preparedStatement.setString(2, key.asString())
                preparedStatement.executeUpdate()
            }
        }

        DATABASE_CACHE[cacheKey] = true

        if (player is Player) {
            // player.sendToast(icon, Component.translatable("chapter.granted", title))
        }
    }

    override fun revoke(player: OfflinePlayer) {
        val cacheKey = Pair(player.uniqueId, this)

        if (DATABASE_CACHE[cacheKey] == false || !isGranted(player)) {
            return
        }

        forEach { it.unachieve(player) }

        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("DELETE FROM ${Vanilife.DATABASE_PLAYER_CHAPTER} WHERE player = ? AND chapter = ?").use { preparedStatement ->
                preparedStatement.setString(1, player.uniqueId.toString())
                preparedStatement.setString(2, key.asString())
                preparedStatement.executeUpdate()
            }
        }

        DATABASE_CACHE[cacheKey] = false
    }

    override fun isGranted(player: OfflinePlayer): Boolean {
        val cacheKey = Pair(player.uniqueId, this)
        val cache = DATABASE_CACHE[cacheKey]

        if (cache != null) {
            return cache
        }

        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("SELECT 1 FROM ${Vanilife.DATABASE_PLAYER_CHAPTER} WHERE player = ? AND chapter = ? LIMIT 1").use { preparedStatement ->
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