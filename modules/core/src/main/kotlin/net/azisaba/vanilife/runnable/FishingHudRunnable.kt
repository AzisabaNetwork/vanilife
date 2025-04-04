package net.azisaba.vanilife.runnable

import net.azisaba.vanilife.extension.sendFishingHud
import org.bukkit.Bukkit
import kotlin.math.sqrt

object FishingHudRunnable: Runnable {
    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val hook = player.fishHook
            if (player.gameMode.isInvulnerable || hook == null) {
                continue
            }

            val hookLocation = hook.location
            val playerLocation = player.location

            val deltaX = hookLocation.x - playerLocation.x
            val deltaZ = hookLocation.z - playerLocation.z

            player.sendFishingHud(sqrt(deltaX * deltaX + deltaZ * deltaZ).toInt(), 0F)
        }
    }
}