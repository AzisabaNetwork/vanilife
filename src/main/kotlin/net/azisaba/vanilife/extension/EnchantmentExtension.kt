package net.azisaba.vanilife.extension

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.azisaba.vanilife.registry.Enchantments
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

val Enchantment.customEnchantment: net.azisaba.vanilife.enchantment.Enchantment?
    get() = Enchantments.values.firstOrNull { it.key == key.toKey() }

val Enchantment.isCustomEnchantment: Boolean
    get() = customEnchantment != null

fun Enchantment.level(itemStack: ItemStack): Int {
    return itemStack.enchantments.entries.firstOrNull { it.key.key == key }?.value ?: 1
}

fun net.azisaba.vanilife.enchantment.Enchantment.paper(): Enchantment {
    return RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(key)!!
}