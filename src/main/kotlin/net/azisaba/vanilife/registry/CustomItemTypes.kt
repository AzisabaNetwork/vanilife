package net.azisaba.vanilife.registry

import net.azisaba.vanilife.item.*
import net.azisaba.vanilife.loot.modifier.LootModifier

object CustomItemTypes: KeyedRegistry<CustomItemType>() {
    val BULK_MINING_BOOK = register(BulkMiningBookItemType)
    val CAVENIUM = register(CaveniumItemType)
    val COMPRESSED_CAVENIUM = register(CompressedCaveniumItemType)
    val MAGNITE = register(MagniteItemType)
    val MAGNITE_MINING_BOOK = register(MagniteMiningBookItemType)
    val MONEY_1000 = register(MoneyItemType.MONEY_1000)
    val MONEY_5000 = register(MoneyItemType.MONEY_5000)
    val MONEY_10000 = register(MoneyItemType.MONEY_10000)
    val RANGE_MINING_BOOK = register(RangeMiningBookItemType)

    override fun <T : CustomItemType> register(value: T): T {
        value.craftingRecipes?.let {
            for (recipe in it) {
                CustomRecipes.register(recipe)
            }
        }

        value.lootModifiers?.let {
            for (modifier in it) {
                LootModifier.register(modifier)
            }
        }
        return super.register(value)
    }
}