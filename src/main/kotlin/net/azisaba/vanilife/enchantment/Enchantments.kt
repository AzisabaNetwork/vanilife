package net.azisaba.vanilife.enchantment

import net.azisaba.vanilife.registry.Registry

object Enchantments: Registry<Enchantment>() {
    val BULK_MINING = register(BulkMiningEnchantment)
    val RANGE_MINING = register(RangeMiningEnchantment)
}