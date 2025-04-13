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

object FoodstuffGroup: KeyedRegistryImpl<CustomItemType>(), ItemGroup {
    override val key: Key = Key.key(PLUGIN_ID, "foodstuff")

    override val icon: ItemElement = Element.item(CustomItemTypes.CHEESE.createItemStack()).hideAdditionalTooltip(true)

    override val title: Component = Component.translatable("itemGroup.vanilife.foodstuff")

    val APPLE_JAM = register(CustomItemTypes.APPLE_JAM)

    val BELL_PEPPER = register(CustomItemTypes.BELL_PEPPER)

    val BUTTER = register(CustomItemTypes.BUTTER)

    val CHEESE = register(CustomItemTypes.CHEESE)

    val DOUGH = register(CustomItemTypes.DOUGH)

    val MAYONNAISE = register(CustomItemTypes.MAYONNAISE)

    val ROCK_SALT = register(CustomItemTypes.ROCK_SALT)

    val SALT = register(CustomItemTypes.SALT)

    val SLICED_TOMATO = register(CustomItemTypes.SLICED_TOMATO)

    val WHEAT_FLOUR = register(CustomItemTypes.WHEAT_FLOUR)

    val YEAST = register(CustomItemTypes.YEAST)
}