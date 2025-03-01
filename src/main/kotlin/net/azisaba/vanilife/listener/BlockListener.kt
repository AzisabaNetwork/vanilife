package net.azisaba.vanilife.listener

import net.azisaba.vanilife.enchantment.ToolEnchantment
import net.azisaba.vanilife.extension.*
import net.azisaba.vanilife.item.BlockItemType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

object BlockListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block

        player.inventory.itemInMainHand.enchantments
            .filter { it.key.customEnchantment is ToolEnchantment }
            .map { it.key.customEnchantment as ToolEnchantment }
            .forEach { it.use(event.player, event.block, event.player.inventory.itemInMainHand, it.paper()) }

        if (! block.isCustomBlock) {
            return
        }

        block.customBlockType!!.itemType?.let {
            event.isDropItems = false
            block.world.dropItem(block.location, ItemStack(it))
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockPlace(event: BlockPlaceEvent) {
        val itemStack = event.itemInHand.takeIf { it.isCustomItem } ?: return
        event.isCancelled = itemStack.customItemType!! is BlockItemType
        event.setBuild(event.isCancelled)
    }
}