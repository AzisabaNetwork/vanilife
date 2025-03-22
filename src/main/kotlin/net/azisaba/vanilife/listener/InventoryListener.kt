package net.azisaba.vanilife.listener

import net.azisaba.vanilife.extension.customItemType
import net.azisaba.vanilife.extension.hasCustomItemType
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.inventory.CraftingRecipe

object InventoryListener: Listener {
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
}