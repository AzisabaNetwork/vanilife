package com.tksimeji.gonunne.item.builder

import com.tksimeji.gonunne.item.CustomItemType
import com.tksimeji.gonunne.registry.impl.RegistryImpl

internal object ItemStackBuilders: RegistryImpl<Class<out CustomItemType>, ItemStackBuilder<*>>() {
    val COMBINABLE = register(CombinableBuilder)

    val CONSUMABLE = register(ConsumableBuilder)

    val FOOD = register(FoodBuilder)

    val SEASONAL = register(SeasonalBuilder)

    private fun <T: ItemStackBuilder<*>> register(creator: T): T {
        return register(creator.clazz, creator)
    }
}