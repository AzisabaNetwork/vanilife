package net.azisaba.vanilife.listener

import net.azisaba.vanilife.extension.customItemType
import net.azisaba.vanilife.extension.isCustomItem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.inventory.CraftingRecipe

object InventoryListener: Listener {
    @EventHandler
    fun onCraftItem(event: CraftItemEvent) {
        val recipe = (event.recipe.takeIf { it is CraftingRecipe } ?: return) as CraftingRecipe
        val matrix = event.inventory.matrix.filterNotNull().takeIf { it.any { item -> item.isCustomItem } } ?: return

        if (matrix.any { it.isCustomItem && it.customItemType!!.key.namespace() != recipe.key.namespace }) {
            event.isCancelled = true
            event.inventory.result = null
        }
    }
}