package com.tksimeji.gonunne.item.group

import com.tksimeji.gonunne.item.CustomItemType
import com.tksimeji.gonunne.key.Keyed
import com.tksimeji.gonunne.registry.KeyedRegistry
import com.tksimeji.kunectron.element.ItemElement
import net.kyori.adventure.text.Component

interface ItemGroup: Keyed, KeyedRegistry<CustomItemType> {
    val icon: ItemElement

    val title: Component
}