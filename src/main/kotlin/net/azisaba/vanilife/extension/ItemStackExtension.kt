package net.azisaba.vanilife.extension

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.item.Item
import net.azisaba.vanilife.registry.Items
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.NamespacedKey
import org.bukkit.Registry
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun ItemStack(item: Item, amount: Int): ItemStack {
    val itemStack = item.type.createItemStack(amount)
    val itemMeta = itemStack.itemMeta

    itemMeta.persistentDataContainer.set(NamespacedKey(Vanilife.PLUGIN_ID, "item"), PersistentDataType.STRING, item.key.asString())

    itemMeta.displayName(item.title.colorIfAbsent(NamedTextColor.WHITE).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE))
    itemMeta.lore(item.lore.map { it.colorIfAbsent(NamedTextColor.GRAY).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE) })
    itemMeta.addItemFlags(*ItemFlag.entries.toTypedArray())

    for (attribute in Registry.ATTRIBUTE) {
        itemMeta.removeAttributeModifier(attribute)
        itemMeta.addAttributeModifier(
            attribute,
            AttributeModifier(
                NamespacedKey(Vanilife.PLUGIN_ID, attribute.key.key),
                0.0,
                AttributeModifier.Operation.ADD_NUMBER
            )
        )
    }

    item.model?.let {
        itemMeta.itemModel = it.toNamespacedKey()
    }

    if (item.aura) {
        itemMeta.addEnchant(Enchantment.INFINITY, 1, false)
    }

    return itemStack.also { it.itemMeta = itemMeta }
}

val ItemStack.item: Item?
    get() {
        val key = (persistentDataContainer.get(NamespacedKey(Vanilife.PLUGIN_ID, "item"), PersistentDataType.STRING) ?: return null).toKey()
        return Items.get(key)
    }

val ItemStack.isItem: Boolean
    get() = item != null