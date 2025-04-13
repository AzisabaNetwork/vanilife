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

object ToolGroup: KeyedRegistryImpl<CustomItemType>(), ItemGroup {
    override val key: Key = Key.key(PLUGIN_ID, "tool")

    override val icon: ItemElement = Element.item(CustomItemTypes.KNIFE.createItemStack()).hideAdditionalTooltip(true)

    override val title: Component = Component.translatable("itemGroup.vanilife.tool")

    val KNIFE = register(CustomItemTypes.KNIFE)
}