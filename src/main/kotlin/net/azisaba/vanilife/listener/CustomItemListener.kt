package net.azisaba.vanilife.listener

import com.destroystokyo.paper.event.server.ServerTickStartEvent
import net.azisaba.vanilife.extension.customItemType
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

object CustomItemListener: Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val itemStack = event.item
        itemStack?.customItemType?.use(event.player)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onServerTickStart(event: ServerTickStartEvent) {
        for (player in Bukkit.getOnlinePlayers()) {
            val inventory = player.inventory

            val mainHand = inventory.itemInMainHand
            mainHand.customItemType?.onInHand(player)
            mainHand.customItemType?.onInMainHand(player)

            val offHand = inventory.itemInOffHand
            offHand.customItemType?.onInHand(player)
            offHand.customItemType?.onInOffHand(player)
        }
    }
}