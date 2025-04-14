package com.tksimeji.gonunne.item.builder

import com.tksimeji.gonunne.component.resetStyle
import com.tksimeji.gonunne.item.Combinable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.inventory.ItemStack

internal object CombinableBuilder: ItemStackBuilder<Combinable> {
    override val clazz: Class<Combinable> = Combinable::class.java

    override fun build(customItemType: Combinable, itemStack: ItemStack) {
        val lore = itemStack.lore() ?: mutableListOf()
        lore.add(Component.translatable("item.gonunne.combinable.description", customItemType.combineHint).color(NamedTextColor.DARK_GRAY).resetStyle())
        itemStack.apply { itemMeta = itemMeta.apply { lore(lore) } }
    }
}