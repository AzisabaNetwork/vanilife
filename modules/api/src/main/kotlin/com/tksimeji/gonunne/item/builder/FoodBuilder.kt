package com.tksimeji.gonunne.item.builder

import com.tksimeji.gonunne.item.Food
import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.FoodProperties
import org.bukkit.inventory.ItemStack

internal object FoodBuilder: ItemStackBuilder<Food> {
    override val clazz: Class<Food> = Food::class.java

    override fun build(customItemType: Food, itemStack: ItemStack) {
        itemStack.setData(DataComponentTypes.FOOD, FoodProperties.food()
            .nutrition(customItemType.nutrition)
            .saturation(customItemType.saturation)
            .canAlwaysEat(customItemType.canAlwaysEat))
    }
}