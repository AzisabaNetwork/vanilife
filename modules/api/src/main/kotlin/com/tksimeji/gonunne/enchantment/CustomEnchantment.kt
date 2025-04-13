package com.tksimeji.gonunne.enchantment

import com.tksimeji.gonunne.enchantment.effect.EnchantmentEffects
import com.tksimeji.gonunne.key.Keyed
import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.tag.TagKey
import net.kyori.adventure.text.Component
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemType

interface CustomEnchantment: Keyed {
    val displayName: Component

    val maxLevel: Int

    val maxCost: EnchantmentRegistryEntry.EnchantmentCost

    val minCost: EnchantmentRegistryEntry.EnchantmentCost

    val anvilCost: Int

    val enchantingTableWeight: Int

    val activeSlots: Set<EquipmentSlotGroup>

    val exclusiveSet: Set<net.kyori.adventure.key.Keyed>
        get() = setOf()

    val supportedItems: TagKey<ItemType>

    val effects: EnchantmentEffects
}