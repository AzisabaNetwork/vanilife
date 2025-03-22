package net.azisaba.vanilife.loot.modifier

import net.azisaba.vanilife.loot.Range
import org.bukkit.inventory.ItemStack

interface RandomizedLootModifier: LootModifier {
    val type: Type

    val itemStack: ItemStack

    val probability: Double

    val amount: Range

    val rolls: Range

    enum class Type {
        ADD,
        MODIFY
    }
}