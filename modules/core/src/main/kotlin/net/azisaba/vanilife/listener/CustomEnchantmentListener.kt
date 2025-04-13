package net.azisaba.vanilife.listener

import com.tksimeji.gonunne.enchantment.context.BlockBreakContext
import com.tksimeji.gonunne.enchantment.effect.EnchantmentEffectEvent
import com.tksimeji.gonunne.enchantment.effect.EnchantmentEffects
import net.azisaba.vanilife.extensions.customEnchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.EquipmentSlot

object CustomEnchantmentListener: Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player

        val itemStack = player.inventory.itemInMainHand
        val customEnchantments = itemStack.enchantments.mapNotNull { it.key.customEnchantment() }.toSet()

        if (customEnchantments.isEmpty()) {
            return
        }

        event.isDropItems = false

        val blocks = mutableSetOf(event.block)

        EnchantmentEffects.call(EnchantmentEffectEvent.BREAK_BLOCK, BlockBreakContext.create(player, itemStack, EquipmentSlot.HAND, blocks), customEnchantments)

        for (block in blocks) {
            block.breakNaturally(itemStack)
        }
    }
}