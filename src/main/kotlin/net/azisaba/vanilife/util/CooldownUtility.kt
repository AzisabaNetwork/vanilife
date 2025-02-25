package net.azisaba.vanilife.util

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.sendError
import net.kyori.adventure.text.Component
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.UUID

private val cooldownMap = mutableMapOf<Pair<Any, UUID>, Long>()

fun runWithCooldown(any: Any, player: Player, cooldown: Long = 20L * 60, runnable: Runnable) {
    val key = Pair(any, player.uniqueId)

    val remaining = cooldownMap[key] ?: run {
        runnable.run()

        if (player.isOp && player.gameMode.isInvulnerable) {
            return
        }

        cooldownMap[key] = cooldown

        object : BukkitRunnable() {
            override fun run() {
                val value = cooldownMap[key] ?: 0

                if (value <= 0) {
                    cooldownMap.remove(key)
                    cancel()
                    return
                } else {
                    cooldownMap[key] = value - 1
                }
            }
        }.runTaskTimerAsynchronously(Vanilife.plugin, 0L, 1L)
        return
    }

    player.playSound(player, Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 0.1f)
    player.sendError(Component.text("クールダウン中だよ！${remaining / 20}秒後にもう一回試してみてね！"))
}