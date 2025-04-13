package com.tksimeji.gonunne.item

import com.tksimeji.gonunne.component.resetStyle
import com.tksimeji.gonunne.item.builder.ItemStackBuilders
import com.tksimeji.gonunne.key.toNamespacedKey
import net.azisaba.vanilife.KEY_CUSTOM_ITEM_TYPE
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.Registry
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import org.bukkit.persistence.PersistentDataType

fun CustomItemType.createItemStack(amount: Int = 1): ItemStack {
    val itemStack = itemType.createItemStack(amount)
    val itemMeta = itemStack.itemMeta

    itemMeta.persistentDataContainer.set(KEY_CUSTOM_ITEM_TYPE.toNamespacedKey(), PersistentDataType.STRING, key.asString())

    itemMeta.displayName(displayName.colorIfAbsent(rarity.color()).resetStyle())
    itemMeta.lore(lore.map { it.colorIfAbsent(NamedTextColor.GRAY).resetStyle() })
    itemMeta.setRarity(rarity)

    itemModel?.let { itemMeta.itemModel = it.toNamespacedKey() }
    if (maxStackSize != itemType.maxStackSize) itemMeta.setMaxStackSize(maxStackSize)
    if (enchantmentAura) itemMeta.addEnchant(Enchantment.INFINITY, 1, false)

    itemStack.itemMeta = itemMeta

    for (builder in ItemStackBuilders) {
        if (builder.clazz.isAssignableFrom(javaClass)) {
            builder.buildUnsafe(this, itemStack)
        }
    }

    return itemStack
}

fun ItemType.toMaterial(): Material {
    return Material.matchMaterial(key.asString())!!
}

fun Material.toItemType(): ItemType {
    return Registry.ITEM.get(key)!!
}