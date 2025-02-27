package net.azisaba.vanilife.listener

import net.azisaba.vanilife.extension.customBlockType
import net.azisaba.vanilife.extension.isCustomItem
import net.azisaba.vanilife.extension.customItemType
import net.azisaba.vanilife.item.BlockItemType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

object PlayerListener: Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (! event.hasBlock() || ! event.clickedBlock!!.isSolid) {
            return
        }

        val itemStack = event.item.takeIf { it?.isCustomItem ?: false } ?: return
        val itemType = (itemStack.customItemType!!.takeIf { it is BlockItemType } ?: return) as BlockItemType

        val block = event.clickedBlock!!.getRelative(event.blockFace)

        block.customBlockType = itemType.blockType
        itemStack.amount -= 1
    }
}