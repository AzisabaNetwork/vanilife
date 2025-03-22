package net.azisaba.vanilife.registry

import net.azisaba.vanilife.enchantment.*

object CustomEnchantments: KeyedRegistry<CustomEnchantment>() {
    val BULK_MINING = register(BulkMiningEnchantment)
    val RANGE_MINING = register(RangeMiningEnchantment)
}