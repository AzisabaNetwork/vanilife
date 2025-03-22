package net.azisaba.vanilife.extension

import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.CraftingInventory

val CraftingInventory.resultSlot: Int
    get() = 0

val CraftingInventory.matrixSlots: List<Int>
    get() {
        return when (type) {
            InventoryType.CRAFTING -> (1..4).toList()
            InventoryType.WORKBENCH -> (1..9).toList()
            else -> throw UnsupportedOperationException()
        }
    }