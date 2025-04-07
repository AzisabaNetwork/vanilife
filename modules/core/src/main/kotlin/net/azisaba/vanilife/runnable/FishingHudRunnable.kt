package net.azisaba.vanilife.runnable

import net.azisaba.vanilife.extension.distance2D
import net.azisaba.vanilife.extension.sendFishingHud
import org.bukkit.Bukkit
import java.util.*

object FishingHudRunnable: Runnable {
    internal val clickTimes = mutableMapOf<UUID, MutableList<Long>>()

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val hook = player.fishHook
            if (player.gameMode.isInvulnerable || hook == null) {
                continue
            }

            val hookLocation = hook.location
            val playerLocation = player.location

            val currentTime = System.currentTimeMillis()
            val timestamps = clickTimes.getOrPut(player.uniqueId) { mutableListOf() }
            timestamps.removeIf { it + 1000 < currentTime }

            player.sendFishingHud(hookLocation.distance2D(playerLocation), timestamps.size)
        }
    }
}