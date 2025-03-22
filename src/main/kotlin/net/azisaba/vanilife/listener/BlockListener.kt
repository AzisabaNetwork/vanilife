package net.azisaba.vanilife.listener

import net.azisaba.vanilife.enchantment.ToolEnchantment
import net.azisaba.vanilife.extension.customEnchantment
import net.azisaba.vanilife.extension.getEnchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object BlockListener: Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block

        player.inventory.itemInMainHand.enchantments
            .mapNotNull { it.key.customEnchantment as? ToolEnchantment }
            .forEach { it.use(player, block, player.inventory.itemInMainHand, it.getEnchantment()) }
    }
}