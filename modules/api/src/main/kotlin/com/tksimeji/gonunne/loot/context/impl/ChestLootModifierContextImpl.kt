package com.tksimeji.gonunne.loot.context.impl

import com.tksimeji.gonunne.loot.context.ChestLootModifierContext
import org.bukkit.inventory.InventoryHolder

internal class ChestLootModifierContextImpl(override val inventory: InventoryHolder) : ChestLootModifierContext {
}