package com.tksimeji.gonunne.enchantment

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta

fun CustomEnchantment.createEnchantedBook(amount: Int = 1, level: Int = 1): ItemStack {
    return toPaperEnchantment().createEnchantedBook(amount, level)
}

fun CustomEnchantment.toPaperEnchantment(): Enchantment {
    return RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).getOrThrow(key)
}

fun Enchantment.createEnchantedBook(amount: Int = 1, level: Int = 1): ItemStack {
    return ItemStack.of(Material.ENCHANTED_BOOK, amount).apply {
        itemMeta = (itemMeta as EnchantmentStorageMeta).apply { addStoredEnchant(this@createEnchantedBook, level, true) }
    }
}

fun ItemStack.getEnchantmentLevel(enchantment: CustomEnchantment): Int {
    return enchantments.entries.firstOrNull { it.key.key == enchantment.key }?.value ?: 0
}