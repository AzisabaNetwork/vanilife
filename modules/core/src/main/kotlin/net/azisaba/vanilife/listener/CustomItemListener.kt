package net.azisaba.vanilife.listener

import com.destroystokyo.paper.event.server.ServerTickStartEvent
import net.azisaba.vanilife.extension.customItemType
import net.azisaba.vanilife.extension.hasCustomItemType
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.CraftingRecipe

object CustomItemListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onCraftItem(event: CraftItemEvent) {
        val recipe = (event.recipe as? CraftingRecipe) ?: return
        val matrix = event.inventory.matrix.filterNotNull()

        if (matrix.none { it.hasCustomItemType() }) {
            return
        }

        if (matrix.any { it.hasCustomItemType() && it.customItemType!!.key.namespace() != recipe.key.namespace }) {
            event.isCancelled = true
            event.inventory.result = null
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val itemStack = event.item
        itemStack?.customItemType?.use(event.player, event.action, event.clickedBlock, event.blockFace)
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