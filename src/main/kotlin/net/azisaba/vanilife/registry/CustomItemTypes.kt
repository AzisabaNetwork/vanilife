package net.azisaba.vanilife.registry

import net.azisaba.vanilife.item.*
import net.azisaba.vanilife.loot.modifier.LootModifier

object CustomItemTypes: KeyedRegistry<CustomItemType>() {
    val APPLE_JAM = register(AppleJamItemType)
    val BACON = register(BaconItemType)
    val BANANA = register(BananaItemType)
    val BULK_MINING_BOOK = register(BulkMiningBookItemType)
    val BUTTER = register(ButterItemType)
    val BUTTERED_POTATO = register(ButteredPotatoItemType)
    val CAVENIUM = register(CaveniumItemType)
    val CHEESE = register(CheeseItemType)
    val CHERRY = register(CherryItemType)
    val COMPRESSED_CAVENIUM = register(CompressedCaveniumItemType)
    val DOUGH = register(DoughItemType)
    val FRUIT_SANDWICH = register(FruitSandwichItemType)
    val GRAPE = register(GrapeItemType)
    val MAGNITE = register(MagniteItemType)
    val MAGNITE_MINING_BOOK = register(MagniteMiningBookItemType)
    val KNIFE = register(KnifeItemType)
    val LETTUCE = register(LettuceItemType)
    val MAYONNAISE = register(MayonnaiseItemType)
    val MONEY_1000 = register(MoneyItemType.MONEY_1000)
    val MONEY_5000 = register(MoneyItemType.MONEY_5000)
    val MONEY_10000 = register(MoneyItemType.MONEY_10000)
    val PEACH = register(PeachItemType)
    val RANGE_MINING_BOOK = register(RangeMiningBookItemType)
    val ROCK_SALT = register(RockSaltItemType)
    val SALT = register(SaltItemType)
    val SANDWICH = register(SandwichItemType)
    val SLICED_TOMATO = register(SlicedTomatoItemType)
    val STRAWBERRY = register(StrawberryItemType)
    val TOMATO = register(TomatoItemType)
    val WHEAT_FLOUR = register(WheatFlourItemType)
    val WHITE_BREAD = register(WhiteBreadItemType)
    val YEAST = register(YeastItemType)

    override fun <T : CustomItemType> register(value: T): T {
        value.lootModifiers?.let {
            for (modifier in it) {
                LootModifier.register(modifier)
            }
        }
        return super.register(value)
    }
}