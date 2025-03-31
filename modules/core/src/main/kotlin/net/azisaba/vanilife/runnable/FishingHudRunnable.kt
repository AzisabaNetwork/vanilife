package net.azisaba.vanilife.runnable

import net.azisaba.vanilife.extension.sendFishingHud
import org.bukkit.Bukkit

object FishingHudRunnable: Runnable {
    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val hook = player.fishHook
            if (player.gameMode.isInvulnerable || hook == null) {
                continue
            }

            player.sendFishingHud(hook.location.distance(player.location).toInt(), 0F)
        }
    }
}