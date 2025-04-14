package com.tksimeji.gonunne.item.builder

import com.tksimeji.gonunne.component.plainText
import com.tksimeji.gonunne.component.resetStyle
import com.tksimeji.gonunne.item.Seasonal
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.inventory.ItemStack

internal object SeasonalBuilder: ItemStackBuilder<Seasonal> {
    override val clazz: Class<Seasonal> = Seasonal::class.java

    override fun build(customItemType: Seasonal, itemStack: ItemStack) {
        val builder = Component.text()

        for (season in customItemType.season) {
            if (builder.build().plainText().isNotEmpty()) {
                builder.append(Component.text(", ").color(NamedTextColor.DARK_GRAY))
            }
            builder.append(Component.translatable(season).color(season.textColor))
        }

        val lore = itemStack.lore() ?: mutableListOf()
        lore.add(Component.translatable("item.gonunne.seasonal.description", builder).color(NamedTextColor.GRAY).resetStyle())
        itemStack.apply { itemMeta = itemMeta.apply { lore(lore) } }
    }
}