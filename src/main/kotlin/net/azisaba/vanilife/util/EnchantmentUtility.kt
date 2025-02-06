package net.azisaba.vanilife.util

import net.azisaba.vanilife.enchantment.Enchantments
import org.bukkit.enchantments.Enchantment

val Enchantment.enchantment: net.azisaba.vanilife.enchantment.Enchantment?
    get() = Enchantments.entries.firstOrNull { it.handle == this }