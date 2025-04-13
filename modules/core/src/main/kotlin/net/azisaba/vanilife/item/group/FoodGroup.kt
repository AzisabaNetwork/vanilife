package net.azisaba.vanilife.item.group

import com.tksimeji.gonunne.item.CustomItemType
import com.tksimeji.gonunne.item.createItemStack
import com.tksimeji.gonunne.item.group.ItemGroup
import com.tksimeji.gonunne.registry.impl.KeyedRegistryImpl
import com.tksimeji.kunectron.element.Element
import com.tksimeji.kunectron.element.ItemElement
import net.azisaba.vanilife.PLUGIN_ID
import net.azisaba.vanilife.registry.CustomItemTypes
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component

object FoodGroup: KeyedRegistryImpl<CustomItemType>(), ItemGroup {
    override val key: Key = Key.key(PLUGIN_ID, "food")

    override val icon: ItemElement = Element.item(CustomItemTypes.SANDWICH.createItemStack())

    override val title: Component = Component.translatable("itemGroup.vanilife.food")

    val BACON = register(CustomItemTypes.BACON)

    val BUTTERED_POTATO = register(CustomItemTypes.BUTTERED_POTATO)

    val FRUIT_SANDWICH = register(CustomItemTypes.FRUIT_SANDWICH)

    val SANDWICH = register(CustomItemTypes.SANDWICH)

    val WHITE_BREAD = register(CustomItemTypes.WHITE_BREAD)
}