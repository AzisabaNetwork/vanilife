package net.azisaba.vanilife.enchantment

import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.tag.TagKey
import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemType

interface CustomEnchantment: Keyed {
    val displayName: Component

    val weight: Int
        get() = 1

    val maximumLevel: Int
        get() = 1

    val maximumCost: EnchantmentRegistryEntry.EnchantmentCost

    val minimumCost: EnchantmentRegistryEntry.EnchantmentCost

    val anvilCost: Int

    val activeSlots: Set<EquipmentSlotGroup>

    val supportedItems: TagKey<ItemType>

    val exclusives: Set<net.kyori.adventure.key.Keyed>
        get() = setOf()
}