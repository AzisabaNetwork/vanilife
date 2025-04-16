package net.azisaba.vanilife.listener

import com.destroystokyo.paper.event.server.ServerTickStartEvent
import com.tksimeji.gonunne.item.Combinable
import net.azisaba.vanilife.extensions.customItemType
import net.azisaba.vanilife.extensions.hasCustomItemType
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
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

        if (matrix.any { it.hasCustomItemType() && it.customItemType()!!.key.namespace() != recipe.key.namespace }) {
            event.isCancelled = true
            event.inventory.result = null
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.action != InventoryAction.SWAP_WITH_CURSOR) {
            return
        }

        val player = event.whoClicked as? Player ?: return

        val inventoryItemStack = event.currentItem ?: return
        val cursorItemStack = event.cursor

        val customItemType = cursorItemStack.customItemType() as? Combinable ?: return
        if (!customItemType.canCombine(inventoryItemStack)) {
            return
        }
        customItemType.combine(player, cursorItemStack, inventoryItemStack)

        event.isCancelled = true
        player.setItemOnCursor(null)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val itemStack = event.item
        itemStack?.customItemType()?.use(itemStack, event.player, event.action, event.clickedBlock, event.blockFace)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onEntityPickupItem(event: EntityPickupItemEvent) {
        val player = event.entity as? Player ?: return
        val itemStack = event.item.itemStack
        itemStack.customItemType()?.onPickup(itemStack, player)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onServerTickStart(event: ServerTickStartEvent) {
        for (player in Bukkit.getOnlinePlayers()) {
            val inventory = player.inventory

            val mainHand = inventory.itemInMainHand
            mainHand.customItemType()?.onInHand(player)
            mainHand.customItemType()?.onInMainHand(player)

            val offHand = inventory.itemInOffHand
            offHand.customItemType()?.onInHand(player)
            offHand.customItemType()?.onInOffHand(player)
        }
    }
}