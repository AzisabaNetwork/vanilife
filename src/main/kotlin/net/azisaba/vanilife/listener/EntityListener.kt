package net.azisaba.vanilife.listener

import net.azisaba.vanilife.extension.cluster
import net.azisaba.vanilife.extension.hasCluster
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTeleportEvent

object EntityListener: Listener {
    @EventHandler
    fun onEntityTeleport(event: EntityTeleportEvent) {
        val from = event.from.takeIf { it.world.hasCluster() } ?: return
        val to = event.to.takeIf { it?.world != from.world } ?: return

        if (! (from.world.environment == World.Environment.THE_END && to.world.environment == World.Environment.NORMAL)) {
            return
        }

        event.to = from.world.cluster!!.overworld.spawnLocation
    }
}