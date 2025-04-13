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

object MoneyGroup: KeyedRegistryImpl<CustomItemType>(), ItemGroup {
    override val key: Key = Key.key(PLUGIN_ID, "money")

    override val icon: ItemElement = Element.item(CustomItemTypes.MONEY_10000.createItemStack())

    override val title: Component = Component.translatable("itemGroup.vanilife.money")

    val MONEY_1000 = register(CustomItemTypes.MONEY_1000)

    val MONEY_5000 = register(CustomItemTypes.MONEY_5000)

    val MONEY_10000 = register(CustomItemTypes.MONEY_10000)
}