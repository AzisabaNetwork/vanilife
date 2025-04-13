package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.item.group.ItemGroup
import com.tksimeji.gonunne.registry.impl.KeyedRegistryImpl
import net.azisaba.vanilife.item.group.*

object ItemGroups: KeyedRegistryImpl<ItemGroup>() {
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