package net.azisaba.vanilife.listener

import com.destroystokyo.paper.event.server.ServerTickStartEvent
import net.azisaba.vanilife.util.getMoney
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object ServerListener: Listener {
    @EventHandler
    fun onServerTickStart(event: ServerTickStartEvent) {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.remainingAir != player.maximumAir) {
                player.sendActionBar(Component.empty())
                continue
            }

            player.sendActionBar(Component.text("\uF000\uE001").append(Component.text(player.getMoney()).font(Key.key("vanilife:2b49a27b-ba83-43a5-b680-a6185984b9b8"))))
        }
    }
}