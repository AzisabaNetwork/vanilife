package net.azisaba.vanilife.extensions

import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.CraftingInventory

val CraftingInventory.resultIndex: Int
    get() = 0

val CraftingInventory.matrixIndexes: Set<Int>
    get() {
        return when (type) {
            InventoryType.CRAFTING -> (1..4).toSet()
            InventoryType.WORKBENCH -> (1..9).toSet()
            else -> throw UnsupportedOperationException()
        }
    }