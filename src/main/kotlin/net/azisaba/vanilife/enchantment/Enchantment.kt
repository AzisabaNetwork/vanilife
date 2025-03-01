package net.azisaba.vanilife.enchantment

import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.tag.TagKey
import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemType

interface Enchantment: Keyed {
    val title: Component
        get() = Component.translatable("enchantment.${key.namespace()}.${key.value()}")

    val maxLevel: Int
        get() = 1

    val maximumCost: EnchantmentRegistryEntry.EnchantmentCost

    val minimumCost: EnchantmentRegistryEntry.EnchantmentCost

    val anvilCost: Int

    val activeSlots: Set<EquipmentSlotGroup>

    val supportedItems: TagKey<ItemType>

    val weight: Int
        get() = 1

    val exclusives: Set<net.kyori.adventure.key.Keyed>
        get() = setOf()
}