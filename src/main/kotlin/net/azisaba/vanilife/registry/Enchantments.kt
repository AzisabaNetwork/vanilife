package net.azisaba.vanilife.registry

import net.azisaba.vanilife.enchantment.BulkMiningEnchantment
import net.azisaba.vanilife.enchantment.Enchantment
import net.azisaba.vanilife.enchantment.RangeMiningEnchantment

object Enchantments: KeyedRegistry<Enchantment>() {
    val BULK_MINING = register(BulkMiningEnchantment)

    val RANGE_MINING = register(RangeMiningEnchantment)
}