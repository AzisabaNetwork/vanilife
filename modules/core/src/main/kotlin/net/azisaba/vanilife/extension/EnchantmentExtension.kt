package net.azisaba.vanilife.extension

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.azisaba.vanilife.enchantment.CustomEnchantment
import net.azisaba.vanilife.registry.CustomEnchantments
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import org.bukkit.inventory.meta.EnchantmentStorageMeta

val Enchantment.customEnchantment: CustomEnchantment?
    get() = CustomEnchantments.values.firstOrNull { it.key == key.key() }

fun Enchantment.hasCustomEnchantment(): Boolean {
    return customEnchantment != null
}

fun Enchantment.createEnchantedBook(amount: Int = 1, level: Int = 1): ItemStack {
    val enchantedBook = ItemType.ENCHANTED_BOOK.createItemStack(amount)
    val meta = (enchantedBook.itemMeta as? EnchantmentStorageMeta)?.apply {
        addStoredEnchant(this@createEnchantedBook, level, true)
    }
    return enchantedBook.apply { itemMeta = meta }
}

fun CustomEnchantment.toPaperEnchantment(): Enchantment {
    return RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(key)!!
}