package net.azisaba.vanilife.registry

import net.azisaba.vanilife.enchantment.*

object CustomEnchantments: KeyedRegistry<CustomEnchantment>() {
    val BULK_MINING = register(BulkMiningEnchantment)
    val MAGNITE_MINING = register(MagniteMiningEnchantment)
    val RANGE_MINING = register(RangeMiningEnchantment)
}