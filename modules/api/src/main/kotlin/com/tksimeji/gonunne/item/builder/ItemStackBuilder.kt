package com.tksimeji.gonunne.item.builder

import com.tksimeji.gonunne.item.CustomItemType
import org.bukkit.inventory.ItemStack

internal interface ItemStackBuilder<T: CustomItemType> {
    val clazz: Class<T>

    fun build(customItemType: T, itemStack: ItemStack)

    @Suppress("UNCHECKED_CAST")
    fun buildUnsafe(customItemType: CustomItemType, itemStack: ItemStack) {
        val aCustomItemType = customItemType as? T ?: return
        build(aCustomItemType, itemStack)
    }
}