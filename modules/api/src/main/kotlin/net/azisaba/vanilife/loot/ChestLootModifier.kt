package net.azisaba.vanilife.loot

import net.kyori.adventure.key.Key
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

interface ChestLootModifier: LootModifier {
    val targets: Set<Key>

    fun chestLoot(inventory: InventoryHolder, loot: List<ItemStack>)
}