package net.azisaba.vanilife.enchantment

import net.azisaba.vanilife.registry.Registry

object Enchantments: Registry<Enchantment>() {
    val BLAST_FURNACE = register(BlastFurnaceEnchantment)
    val BULK_MINING = register(BulkMiningEnchantment)
    val RANGE_MINING = register(RangeMiningEnchantment)
}