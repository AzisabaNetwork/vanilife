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

object FishGroup: KeyedRegistryImpl<CustomItemType>(), ItemGroup {
    override val key: Key = Key.key(PLUGIN_ID, "fish")

    override val icon: ItemElement = Element.item(CustomItemTypes.CRUCIAN_CARP.createItemStack()).hideAdditionalTooltip(true)

    override val title: Component = Component.translatable("itemGroup.vanilife.fish")

    val CRUCIAN_CARP = register(CustomItemTypes.CRUCIAN_CARP)

    val HORSE_MACKEREL = register(CustomItemTypes.HORSE_MACKEREL)

    val MACKEREL = register(CustomItemTypes.MACKEREL)

    val PACIFIC_SAURY = register(CustomItemTypes.PACIFIC_SAURY)

    val SEA_BREAM = register(CustomItemTypes.SEA_BREAM)

    val SWEET_FISH = register(CustomItemTypes.SWEETFISH)

    val TUNA = register(CustomItemTypes.TUNA)

    val YELLOWTAIL = register(CustomItemTypes.YELLOWTAIL)
}