package net.azisaba.vanilife.listener

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.ItemStack
import net.azisaba.vanilife.extension.customItemType
import net.azisaba.vanilife.extension.giveItemStack
import net.azisaba.vanilife.item.BillItemType
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object ExchangeListener: Listener {
    private val exchangeMap = run {
        val map = mutableMapOf<ItemStack, ItemStack>()

        for (billType in BillItemType.types) {
            for (i in 1..10) {
                val result = BillItemType.types.entries
                    .filter { it != billType }
                    .firstOrNull { it.key == billType.key * i } ?: continue

                map.putIfAbsent(ItemStack(billType.value, i), ItemStack(result.value))
                map.putIfAbsent(ItemStack(result.value), ItemStack(billType.value, i))
            }
        }

        map.toMap()
    }

    private const val RESULT_SLOT = 0

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onInventoryClick(event: InventoryClickEvent) {
        val inventory = event.clickedInventory.takeIf { it is CraftingInventory } ?: return
        val inventoryType = inventory.type.takeIf { it == InventoryType.CRAFTING || it == InventoryType.WORKBENCH } ?: return
        val ingredientSlots = when (inventoryType) {
            InventoryType.CRAFTING -> (1..4).toList()
            InventoryType.WORKBENCH -> (1..9).toList()
            else -> throw IllegalStateException()
        }

        val slot = event.slot

        if (ingredientSlots.contains(slot)) {
            updateExchange(inventory, ingredientSlots)
        } else if (slot == RESULT_SLOT) {
            exchange(event.whoClicked, inventory, event.action, ingredientSlots)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onInventoryDrag(event: InventoryDragEvent) {
        val inventory = event.inventory.takeIf { it is CraftingInventory } ?: return
        val inventoryType = inventory.type.takeIf { it == InventoryType.CRAFTING || it == InventoryType.WORKBENCH } ?: return
        val ingredientSlots = when (inventoryType) {
            InventoryType.CRAFTING -> (1..4).toList()
            InventoryType.WORKBENCH -> (1..9).toList()
            else -> throw IllegalStateException()
        }

        if (event.inventorySlots.any { it in ingredientSlots }) {
            updateExchange(inventory, ingredientSlots)
        }
    }

    private fun exchange(player: HumanEntity, inventory: Inventory, action: InventoryAction, ingredientSlots: List<Int>, resultSlot: Int = RESULT_SLOT) {
        if (! player.itemOnCursor.isEmpty) {
            return
        }

        val result = (inventory.getItem(resultSlot).takeIf { it?.customItemType is BillItemType } ?: return).also {
            if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                player.giveItemStack(it)
            } else {
                player.setItemOnCursor(it)
            }
        }
        val resultType = result.customItemType!! as BillItemType
        val resultPrice = resultType.price * result.amount

        val ingredientMap = ingredientSlots.filter { inventory.getItem(it) != null }
            .map { it to inventory.getItem(it)!! }
            .takeIf { it.isNotEmpty() && it.all {
                    entry -> entry.second.customItemType is BillItemType && entry.second.isSimilar(it.first().second)
            } } ?: return
        val ingredientType = ingredientMap.first().second.customItemType!! as BillItemType

        var remainingAmount = resultPrice / ingredientType.price

        for ((index, ingredient) in ingredientMap) {
            val remove = if (remainingAmount < ingredient.amount) remainingAmount else ingredient.amount
            inventory.setItem(index, ingredient.apply { amount -= remove })
            remainingAmount -= remove
        }

        updateExchange(inventory, ingredientSlots, resultSlot)
    }

    private fun updateExchange(inventory: Inventory, ingredientSlots: List<Int>, resultSlot: Int = RESULT_SLOT) {
        Bukkit.getScheduler().runTaskLater(Vanilife.plugin, { ->
            val ingredients = ingredientSlots.mapNotNull { inventory.getItem(it) }

            if (ingredients.isEmpty() || ingredients.any { it.customItemType !is BillItemType || ! it.isSimilar(ingredients.first()) }) {
                return@runTaskLater
            }

            val amount = ingredients.sumOf { it.amount }
            val input = ingredients.first().clone().apply { this.amount = amount }

            inventory.clear(resultSlot)

            for (i in amount downTo 0) {
                val result = (exchangeMap[input.clone().apply { this.amount = i }] ?: continue).clone()
                for (j in 1..(amount / i)) {
                    val resultSlotItem = inventory.getItem(resultSlot)

                    if (resultSlotItem != null) {
                        resultSlotItem.amount += result.amount
                    } else {
                        inventory.setItem(resultSlot, result)
                    }
                }
                break
            }
        }, 1L)
    }
}