package com.tksimeji.gonunne.recipe

import com.tksimeji.gonunne.item.CustomItemType
import com.tksimeji.gonunne.item.toMaterial
import com.tksimeji.gonunne.key.toNamespacedKey
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe

class DamageableShapelessRecipe(key: Key, result: ItemStack): ShapelessRecipe(key.toNamespacedKey(), result) {
    val damageableIngredients: List<DamageableIngredient>
        get() = _damageableIngredients.toList()

    private var _damageableIngredients: MutableList<DamageableIngredient> = mutableListOf()

    fun addDamageableIngredient(type: Material, damage: Int = 1) {
        _damageableIngredients.add(DamageableIngredient(type, damage))
        addIngredient(type)
    }

    fun addDamageableIngredient(type: CustomItemType, damage: Int = 1) {
        _damageableIngredients.add(DamageableIngredient(type, damage))
        addIngredient(type.itemType.toMaterial())
    }

    data class DamageableIngredient(val type: Keyed, val damage: Int)
}