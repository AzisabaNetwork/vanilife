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

object FruitGroup: KeyedRegistryImpl<CustomItemType>(), ItemGroup {
    override val key: Key = Key.key(PLUGIN_ID, "fruit")

    override val icon: ItemElement = Element.item(CustomItemTypes.STRAWBERRY.createItemStack())

    override val title: Component = Component.translatable("itemGroup.vanilife.fruit")

    val BANANA = register(CustomItemTypes.BANANA)

    val CHERRY = register(CustomItemTypes.CHERRY)

    val GRAPE = register(CustomItemTypes.GRAPE)

    val PEACH = register(CustomItemTypes.PEACH)

    val STRAWBERRY = register(CustomItemTypes.STRAWBERRY)
}