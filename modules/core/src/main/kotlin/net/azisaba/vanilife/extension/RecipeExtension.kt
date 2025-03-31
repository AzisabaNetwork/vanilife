package net.azisaba.vanilife.extension

import net.azisaba.vanilife.item.CustomItemType
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.Sound
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.ItemType
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

internal val RECIPE_EXTENSION_INGREDIENTS = mutableSetOf<Triple<Key, Keyed, Int>>()

internal val RECIPE_EXTENSION_HANDLERS = mutableMapOf<Key, (CraftItemEvent) -> Unit>()

fun ShapedRecipe.setDamageableIngredient(key: Char, type: ItemType, cost: Int = 1) {
    setIngredient(key, type.toMaterial())
    RECIPE_EXTENSION_INGREDIENTS.add(Triple(this.key, type, cost))
}

fun ShapedRecipe.setDamageableIngredient(key: Char, type: CustomItemType, cost: Int = 1) {
    setIngredient(key, type.itemType.toMaterial())
    RECIPE_EXTENSION_INGREDIENTS.add(Triple(this.key, type, cost))
}

fun ShapelessRecipe.addDamageableIngredient(type: ItemType, cost: Int = 1) {
    addIngredient(type.toMaterial())
    RECIPE_EXTENSION_INGREDIENTS.add(Triple(key, type, cost))
}

fun ShapelessRecipe.addDamageableIngredient(type: CustomItemType, cost: Int = 1) {
    addIngredient(type.itemType.toMaterial())
    RECIPE_EXTENSION_INGREDIENTS.add(Triple(key, type, cost))
}

fun CraftingRecipe.setCraftSound(sound: Sound, volume: Float = 1.0F, pitch: Float = 1.0F) {
    onCraft { event ->
        val location = event.inventory.location
        location?.world?.playSound(location, sound, volume, pitch)
    }
}

fun CraftingRecipe.onCraft(handler: (CraftItemEvent) -> Unit) {
    RECIPE_EXTENSION_HANDLERS[key] = handler
}