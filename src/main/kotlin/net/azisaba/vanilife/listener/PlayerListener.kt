package net.azisaba.vanilife.listener

import io.papermc.paper.event.player.AsyncChatEvent
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.util.getArmorStand
import net.azisaba.vanilife.util.plainText
import net.azisaba.vanilife.util.serialize
import net.azisaba.vanilife.util.setArmorStand
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerListener: Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.setArmorStand(player.world.spawnEntity(player.location, EntityType.ARMOR_STAND) as ArmorStand)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.player.getArmorStand().remove()
    }

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        event.player.getArmorStand().teleport(event.to.clone().add(0.0, 0.25, 0.0))
    }

    @EventHandler
    fun onAsyncChat(event: AsyncChatEvent) {
        val player = event.player
        val armorStand = player.getArmorStand()

        val maxLength = 12

        val message = if (event.message().plainText().length > maxLength) {
            "${event.message().plainText().substring(0, maxLength)}…"
        } else {
            event.message().plainText()
        }

        armorStand.customName(Component.text("\uE000").append(Component.text(message)))
        armorStand.isCustomNameVisible = true

        Bukkit.getScheduler().runTaskLater(Vanilife.plugin(), { ->
            if (armorStand.customName()?.serialize()?.substring(1) != message) {
                return@runTaskLater
            }

            armorStand.isCustomNameVisible = false
            armorStand.customName(null)
        }, 20L * 4)
    }
}