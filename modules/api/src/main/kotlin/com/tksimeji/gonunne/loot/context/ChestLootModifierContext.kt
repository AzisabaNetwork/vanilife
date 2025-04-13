package com.tksimeji.gonunne.loot.context

import com.tksimeji.gonunne.loot.context.impl.ChestLootModifierContextImpl
import org.bukkit.inventory.InventoryHolder

interface ChestLootModifierContext: LootModifierContext {
    val inventory: InventoryHolder

    companion object {
        @JvmStatic
        fun create(inventory: InventoryHolder): ChestLootModifierContext {
            return ChestLootModifierContextImpl(inventory)
        }
    }
}