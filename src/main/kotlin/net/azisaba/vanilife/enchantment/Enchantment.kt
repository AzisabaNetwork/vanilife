package net.azisaba.vanilife.enchantment

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.TypedKey
import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.event.RegistryFreezeEvent
import io.papermc.paper.registry.set.RegistryKeySet
import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemType

interface Enchantment: Keyed {
    val name: Component

    val maxLevel: Int
        get() = 1

    val anvilCost: Int

    val minimumCost: EnchantmentRegistryEntry.EnchantmentCost

    val maximumCost: EnchantmentRegistryEntry.EnchantmentCost

    val activeSlots: Set<EquipmentSlotGroup>

    val exclusives: Set<net.kyori.adventure.key.Keyed>
        get() = setOf()

    val weight: Int
        get() = 1

    val handle: Enchantment
        get() = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT)
            .get(TypedKey.create(RegistryKey.ENCHANTMENT, key.asString()))!!

    fun tick(player: Player) {}

    fun createSupportedItems(event: RegistryFreezeEvent<*, *>): RegistryKeySet<ItemType>
}