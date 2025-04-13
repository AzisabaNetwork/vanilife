package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.enchantment.CustomEnchantment
import com.tksimeji.gonunne.registry.impl.KeyedRegistryImpl
import net.azisaba.vanilife.enchantment.*

object CustomEnchantments: KeyedRegistryImpl<CustomEnchantment>() {
    val BULK_MINING = register(BulkMiningEnchantment)
    val MAGNITE_MINING = register(MagniteMiningEnchantment)
    val RANGE_MINING = register(RangeMiningEnchantment)
}