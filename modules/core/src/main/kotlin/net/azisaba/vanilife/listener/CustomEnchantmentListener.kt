package net.azisaba.vanilife.listener

import net.azisaba.vanilife.enchantment.ToolEnchantment
import net.azisaba.vanilife.extension.customEnchantment
import net.azisaba.vanilife.extension.toPaperEnchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object CustomEnchantmentListener: Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player

        val itemStack = player.inventory.itemInMainHand
        val customEnchantments = itemStack.enchantments
            .mapNotNull { it.key.customEnchantment as? ToolEnchantment }
            .sortedBy { it.usePriority }

        if (customEnchantments.isEmpty()) {
            return
        }

        event.isDropItems = false

        val blocks = mutableSetOf(event.block)

        for (customEnchantment in customEnchantments) {
            customEnchantment.use(player, blocks, itemStack, customEnchantment.toPaperEnchantment())
        }

        for (block in blocks) {
            block.breakNaturally(itemStack)
        }
    }
}