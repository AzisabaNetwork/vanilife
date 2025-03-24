package net.azisaba.vanilife.extension

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.Consumable
import io.papermc.paper.datacomponent.item.FoodProperties
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.item.CustomItemType
import net.azisaba.vanilife.item.Food
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

    if (customItemType.maxStackSize != customItemType.itemType.maxStackSize) {
        itemMeta.setMaxStackSize(customItemType.maxStackSize)
    }

    if (customItemType.enchantmentAura) {
        itemMeta.addEnchant(Enchantment.INFINITY, 1, false)
    }

    itemStack.setItemMeta(itemMeta)

    if (customItemType is net.azisaba.vanilife.item.Consumable) {
        itemStack.setData(DataComponentTypes.CONSUMABLE, Consumable.consumable().apply {
            customItemType.consumeSeconds?.let {
                consumeSeconds(it)
            }
            customItemType.consumeAnimation?.let {
                animation(it)
            }
            customItemType.consumeSound?.let {
                sound(it)
            }
            customItemType.consumeEffects?.let {
                addEffects(it)
            }
            customItemType.hasConsumeParticles?.let {
                hasConsumeParticles(it)
            }
        })
    }

    if (customItemType is Food) {
        itemStack.setData(DataComponentTypes.FOOD, FoodProperties.food().apply {
            customItemType.foodNutrition?.let {
                nutrition(it)
            }
            customItemType.foodSaturation?.let {
                saturation(it)
            }
            customItemType.foodCanAlwaysEat?.let {
                canAlwaysEat(it)
            }
        })
    }
    return itemStack
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