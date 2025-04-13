package net.azisaba.vanilife.extensions

import com.tksimeji.gonunne.enchantment.CustomEnchantment
import net.azisaba.vanilife.registry.CustomEnchantments
import org.bukkit.enchantments.Enchantment

fun Enchantment.customEnchantment(): CustomEnchantment? {
    return CustomEnchantments.get(key)
}

fun Enchantment.hasCustomEnchantment(): Boolean {
    return customEnchantment() != null
}