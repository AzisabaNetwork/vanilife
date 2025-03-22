package net.azisaba.vanilife.extension

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.item.CustomItemType
import net.azisaba.vanilife.registry.CustomItemTypes
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.Registry
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import org.bukkit.persistence.PersistentDataType

private val KEY_CUSTOM_ITEM_TYPE = Key.key(Vanilife.PLUGIN_ID, "custom_item_type")

fun ItemStack(customItemType: CustomItemType, amount: Int = 1): ItemStack {
    val itemStack = customItemType.itemType.createItemStack(amount)
    val itemMeta = itemStack.itemMeta

    itemMeta.persistentDataContainer.set(KEY_CUSTOM_ITEM_TYPE.toNamespacedKey(), PersistentDataType.STRING, customItemType.key.asString())

    itemMeta.displayName(customItemType.displayName.asComponent().colorIfAbsent(customItemType.rarity.color()).normalized())
    itemMeta.lore(customItemType.lore.map { it.normalized() })
    itemMeta.setRarity(customItemType.rarity)
    itemMeta.addItemFlags(*ItemFlag.entries.toTypedArray())

    for (attribute in Registry.ATTRIBUTE) {
        itemMeta.removeAttributeModifier(attribute)
        itemMeta.addAttributeModifier(
            attribute,
            AttributeModifier(
                Key.key(Vanilife.PLUGIN_ID, attribute.key().value()).toNamespacedKey(),
                0.0,
                AttributeModifier.Operation.ADD_NUMBER
            )
        )
    }

    customItemType.itemModel?.let {
        itemMeta.itemModel = it.toNamespacedKey()
    }

    if (customItemType.enchantmentAura) {
        itemMeta.addEnchant(Enchantment.INFINITY, 1, false)
    }

    if (customItemType.maxStackSize != customItemType.itemType.maxStackSize) {
        itemMeta.setMaxStackSize(customItemType.maxStackSize)
    }

    return itemStack.apply { this.itemMeta = itemMeta }
}

val ItemStack.customItemType: CustomItemType?
    get() {
        val key = (persistentDataContainer.get(KEY_CUSTOM_ITEM_TYPE.toNamespacedKey(), PersistentDataType.STRING) ?: return null).toKey()
        return CustomItemTypes.get(key)
    }

fun ItemStack.hasCustomItemType(): Boolean {
    return customItemType != null
}

fun ItemStack.getEnchantmentLevel(enchantment: Enchantment): Int {
    return enchantments.entries.firstOrNull { it.key.key == enchantment.key }?.value ?: -1
}

fun ItemType.toMaterial(): Material {
    return Material.matchMaterial(key.asString())!!
}

fun Material.toItemType(): ItemType {
    return Registry.ITEM.get(key)!!
}