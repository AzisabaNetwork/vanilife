package net.azisaba.vanilife.registry

import net.azisaba.vanilife.item.*
import net.azisaba.vanilife.loot.modifier.LootModifier

object CustomItemTypes: KeyedRegistry<CustomItemType>() {
    val APPLE_JAM = register(AppleJamItemType)
    val BACON = register(BaconItemType)
    val BANANA = register(BananaItemType)
    val BELL_PEPPER = register(BellPepperItemType)
    val BULK_MINING_BOOK = register(BulkMiningBookItemType)
    val BUTTER = register(ButterItemType)
    val BUTTERED_POTATO = register(ButteredPotatoItemType)
    val CAVE_STORY = register(CaveStoryItemType)
    val CAVENIUM = register(CaveniumItemType)
    val CHEESE = register(CheeseItemType)
    val CHERRY = register(CherryItemType)
    val COMPRESSED_CAVENIUM = register(CompressedCaveniumItemType)
    val CUCUMBER = register(CucumberItemType)
    val DOUGH = register(DoughItemType)
    val EGGPLANT = register(EggplantItemType)
    val FRUIT_SANDWICH = register(FruitSandwichItemType)
    val GRAPE = register(GrapeItemType)
    val JAPANESE_RADISH = register(JapaneseRadishItemType)
    val KNIFE = register(KnifeItemType)
    val LETTUCE = register(LettuceItemType)
    val MAGNITE = register(MagniteItemType)
    val MAGNITE_MINING_BOOK = register(MagniteMiningBookItemType)
    val MAYONNAISE = register(MayonnaiseItemType)
    val MONEY_1000 = register(MoneyItemType.MONEY_1000)
    val MONEY_5000 = register(MoneyItemType.MONEY_5000)
    val MONEY_10000 = register(MoneyItemType.MONEY_10000)
    val NAPPA_CABBAGE = register(NappaCabbageItemType)
    val ONION = register(OnionItemType)
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