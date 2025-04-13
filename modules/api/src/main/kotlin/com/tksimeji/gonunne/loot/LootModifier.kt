package com.tksimeji.gonunne.loot

import com.tksimeji.gonunne.loot.context.LootModifierContext
import org.bukkit.inventory.ItemStack

interface LootModifier<T, C: LootModifierContext> {
    val targets: Set<T>

    fun loot(context: C, loot: MutableList<ItemStack>)
}