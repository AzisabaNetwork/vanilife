package net.azisaba.vanilife.extension

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.FoodProperties
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.enchantment.CustomEnchantment
import net.azisaba.vanilife.item.Consumable
import net.azisaba.vanilife.item.CustomItemType
import net.azisaba.vanilife.item.Food
import net.azisaba.vanilife.item.Seasonal
import net.azisaba.vanilife.registry.CustomItemTypes
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.Registry
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import org.bukkit.persistence.PersistentDataType
import org.jetbrains.annotations.Range


private val KEY_CUSTOM_ITEM_TYPE = Key.key(Vanilife.PLUGIN_ID, "custom_item_type")

fun CustomItemType.createItemStack(amount: @Range(from = 1, to = 99) Int = 1): ItemStack {
    val itemStack = itemType.createItemStack(amount)
    val itemMeta = itemStack.itemMeta

    itemMeta.persistentDataContainer.set(KEY_CUSTOM_ITEM_TYPE.toNamespacedKey(), PersistentDataType.STRING, key.asString())

    itemMeta.displayName(displayName.colorIfAbsent(rarity.color()).normalized())
    itemMeta.lore(lore.map { it.colorIfAbsent(NamedTextColor.GRAY).normalized() })
    itemMeta.setRarity(rarity)
    itemMeta.addItemFlags(*ItemFlag.entries.toTypedArray())

    for (attribute in Registry.ATTRIBUTE) {
        itemMeta.removeAttributeModifier(attribute)
        itemMeta.addAttributeModifier(attribute, AttributeModifier(
            Key.key(Vanilife.PLUGIN_ID, attribute.key().value()).toNamespacedKey(),
            0.0,
            AttributeModifier.Operation.ADD_NUMBER
        ))
    }

    itemModel?.let { itemMeta.itemModel = it.toNamespacedKey() }

    if (maxStackSize != itemType.maxStackSize) {
        itemMeta.setMaxStackSize(maxStackSize)
    }

    if (enchantmentAura) {
        itemMeta.addEnchant(Enchantment.INFINITY, 1, false)
    }

    if (this is Seasonal) {
        val text = Component.text()
        for (season in season) {
            if (text.plainText().isNotEmpty()) {
                text.append(Component.text(", ").color(NamedTextColor.DARK_GRAY))
            }
            text.append(Component.translatable("season.${season.name.lowercase()}").color(season.color))
        }
        itemMeta.lore((itemMeta.lore() ?: mutableListOf()).apply {
            add(Component.translatable("season.seasonal").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(": ")).color(NamedTextColor.GRAY)
                .append(text))
        })
    }

    itemStack.setItemMeta(itemMeta)

    if (this is Consumable) {
        itemStack.setData(DataComponentTypes.CONSUMABLE, io.papermc.paper.datacomponent.item.Consumable.consumable().apply {
            consumeSeconds?.let { consumeSeconds(it) }
            consumeAnimation?.let { animation(it) }
            consumeSound?.let { sound(it) }
            consumeEffects?.let { addEffects(it) }
            hasConsumeParticles?.let { hasConsumeParticles(it) }
        })
    }

    if (this is Food) {
        itemStack.setData(DataComponentTypes.FOOD, FoodProperties.food().apply {
            nutrition?.let { nutrition(it) }
            saturation?.let { saturation(it) }
            canAlwaysEat?.let { canAlwaysEat(it) }
        })
    }

    return itemStack
}

val ItemStack.customItemType: CustomItemType?
    get() {
        val key = (persistentDataContainer.get(KEY_CUSTOM_ITEM_TYPE.toNamespacedKey(), PersistentDataType.STRING) ?: return null).toKey()
        return CustomItemTypes.get(key)
    }

fun ItemStack.getEnchantmentLevel(enchantment: CustomEnchantment): Int {
    return enchantments.entries.firstOrNull { it.key.key == enchantment.key }?.value ?: 0
}

fun ItemStack.hasCustomItemType(): Boolean {
    return customItemType != null
}

fun ItemType.toMaterial(): Material {
    return Material.matchMaterial(key.asString())!!
}

fun Material.toItemType(): ItemType {
    return Registry.ITEM.get(key)!!
}