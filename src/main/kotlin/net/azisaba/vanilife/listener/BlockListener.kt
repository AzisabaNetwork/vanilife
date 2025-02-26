package net.azisaba.vanilife.listener

import net.azisaba.vanilife.extension.customBlockType
import net.azisaba.vanilife.extension.isCustomItem
import net.azisaba.vanilife.extension.customItemType
import net.azisaba.vanilife.item.BlockItemType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

object BlockListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        block.customBlockType = null
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockPlace(event: BlockPlaceEvent) {
        val itemStack = event.itemInHand.takeIf { it.isCustomItem } ?: return
        event.isCancelled = itemStack.customItemType!! is BlockItemType
    }
}