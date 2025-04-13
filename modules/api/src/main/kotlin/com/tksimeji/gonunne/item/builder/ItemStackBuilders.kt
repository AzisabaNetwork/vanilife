package com.tksimeji.gonunne.item.builder

import com.tksimeji.gonunne.item.CustomItemType
import com.tksimeji.gonunne.registry.impl.RegistryImpl

internal object ItemStackBuilders: RegistryImpl<Class<out CustomItemType>, ItemStackBuilder<*>>() {
    internal val CONSUMABLE = register(ConsumableBuilder)

    internal val FOOD = register(FoodBuilder)

    internal val SEASONAL = register(SeasonalBuilder)

    private fun <T: ItemStackBuilder<*>> register(creator: T): T {
        return register(creator.clazz, creator)
    }
}