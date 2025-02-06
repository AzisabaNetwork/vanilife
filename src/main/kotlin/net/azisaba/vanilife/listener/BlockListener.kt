package net.azisaba.vanilife.listener

import net.azisaba.vanilife.enchantment.ToolEnchantment
import net.azisaba.vanilife.util.enchantment
import net.azisaba.vanilife.util.hand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object BlockListener: Listener {
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val itemStack = player.hand

        itemStack.enchantments
            .filter { it.key.enchantment is ToolEnchantment }
            .map { it.key.enchantment as ToolEnchantment }
            .forEach { it.use(event) }
    }
}