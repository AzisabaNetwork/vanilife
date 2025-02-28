package net.azisaba.vanilife.listener

import com.destroystokyo.paper.event.server.ServerTickStartEvent
import net.azisaba.vanilife.block.Tickable
import net.azisaba.vanilife.extension.getCustomBlocks
import net.azisaba.vanilife.registry.BlockTypes
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object ServerListener: Listener {
    @EventHandler
    fun onServerTickStart(event: ServerTickStartEvent) {
        for (world in Bukkit.getWorlds()) {
            for (blockType in BlockTypes.filterIsInstance<Tickable>()) {
                for (block in world.getCustomBlocks(blockType, true)) {
                    blockType.tick(block)
                }
            }
        }
    }
}