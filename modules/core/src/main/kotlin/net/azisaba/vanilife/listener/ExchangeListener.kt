package net.azisaba.vanilife.listener

import com.tksimeji.gonunne.item.createItemStack
import net.azisaba.vanilife.extensions.customItemType
import net.azisaba.vanilife.extensions.matrixIndexes
import net.azisaba.vanilife.extensions.resultIndex
import net.azisaba.vanilife.item.Money
import net.azisaba.vanilife.util.runTaskLater
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.CraftingInventory
import java.util.*

object ExchangeListener: Listener {
    private val updating = mutableSetOf<UUID>()

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = (event.whoClicked as? Player) ?: return
        val inventory = (event.clickedInventory as? CraftingInventory) ?: return
        val slot = event.slot
        if (inventory.matrixIndexes.contains(slot)) {
            updateResult(ExchangeInfo(player, inventory, event.action))
        } else if (slot == inventory.resultIndex) {
            takeResult(ExchangeInfo(player, inventory, event.action))
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onInventoryDrag(event: InventoryDragEvent) {
        val player = (event.whoClicked as? Player) ?: return
        val inventory = (event.inventorySlots as? CraftingInventory) ?: return
        if (event.inventorySlots.any { it in inventory.matrixIndexes }) {
            updateResult(ExchangeInfo(player, inventory))
        }
    }

    private fun takeResult(info: ExchangeInfo) {
        val player = info.player
        val inventory = info.inventory

        if (updating.contains(player.uniqueId)) {
            return
        }

        if (!player.itemOnCursor.isEmpty) {
            return
        }

        val inputs = inventory.matrixIndexes.filter { inventory.getItem(it) != null }
            .map { it to inventory.getItem(it)!! }
            .takeIf { it.isNotEmpty() && it.all { entry -> entry.second.customItemType() is Money && entry.second.isSimilar(it.first().second) } } ?: return
        val inputType = inputs.first().second.customItemType()!! as Money

        val result = inventory.getItem(inventory.resultIndex)
        val resultType = (result?.customItemType() as? Money) ?: return

        var amount = if (resultType.maxStackSize < result.amount) resultType.maxStackSize else result.amount

        while ((resultType.price * amount) % inputType.price != 0) {
            amount--
        }

        result.apply { this.amount -= amount }

        if (info.action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            player.give(resultType.createItemStack(amount))
        } else {
            player.setItemOnCursor(resultType.createItemStack(amount))
        }

        val resultPrice = resultType.price * amount
        var requirement = resultPrice / inputType.price

        for ((index, input) in inputs) {
            if (requirement <= 0) {
                break
            }

            val removes = if (input.amount < requirement) input.amount else requirement
            inventory.setItem(index, input.apply { this.amount -= removes })
            requirement -= removes
        }

        updateResult(info)
    }

    private fun updateResult(info: ExchangeInfo) {
        val uuid = info.player.uniqueId
        updating.add(uuid)
        runTaskLater(1) {
            val inventory = info.inventory
            val inputs = inventory.matrixIndexes.mapNotNull { inventory.getItem(it) }

            if (inputs.isEmpty() || inputs.any { it.customItemType() !is Money || !it.isSimilar(inputs.first()) }) {
                return@runTaskLater
            }

            val amount = inputs.sumOf { it.amount }

            inventory.clear(inventory.resultIndex)

            for (i in amount downTo 0) {
                val result = (Money.EXCHANGE_MAP[inputs.first().clone().apply { this.amount = i }] ?: continue).clone()
                for (j in 1..(amount / i)) {
                    val resultSlotItem = inventory.getItem(inventory.resultIndex)
                    if (resultSlotItem != null) {
                        resultSlotItem.amount += result.amount
                    } else {
                        inventory.setItem(inventory.resultIndex, result)
                    }
                }
                break
            }
            updating.remove(uuid)
        }
    }

    private data class ExchangeInfo(
        val player: Player,
        val inventory: CraftingInventory,
        val action: InventoryAction? = InventoryAction.NOTHING
    )
}