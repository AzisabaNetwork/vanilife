package net.azisaba.vanilife.registry

import net.azisaba.vanilife.item.ItemGroup
import net.azisaba.vanilife.item.group.*

object ItemGroups: KeyedRegistry<ItemGroup>() {
    val ALL = register(AllGroup)

    val FISH = register(FishGroup)

    val FOOD = register(FoodGroup)

    val FOODSTUFF = register(FoodstuffGroup)

    val FRUIT = register(FruitGroup)

    val MATERIAL = register(MaterialGroup)

    val MONEY = register(MoneyGroup)

    val TOOL = register(ToolGroup)

    val VEGETABLE = register(VegetableGroup)
}