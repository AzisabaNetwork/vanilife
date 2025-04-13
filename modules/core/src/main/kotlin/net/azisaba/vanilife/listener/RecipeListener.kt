package net.azisaba.vanilife.listener

import com.tksimeji.gonunne.recipe.DamageableShapelessRecipe
import net.azisaba.vanilife.extensions.customItemType
import net.azisaba.vanilife.registry.CustomRecipes
import net.azisaba.vanilife.util.runTaskLater
import net.kyori.adventure.key.Keyed
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable

object RecipeListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onCraftItem(event: CraftItemEvent) {
        val recipe = event.recipe

        if (recipe !is Keyed) {
            return
        }

        val damageableShapelessRecipe = CustomRecipes.get(recipe.key()) as? DamageableShapelessRecipe ?: return
        val inventory = event.inventory

        val matrix = inventory.matrix.map { it?.clone() }.toTypedArray()
        val result = mutableMapOf<Int, ItemStack?>()

        for (damageableIngredient in damageableShapelessRecipe.damageableIngredients) {
            for ((index, ingredient) in matrix.withIndex()) {
                if (ingredient == null || (ingredient.type != damageableIngredient.type && ingredient.customItemType() != damageableIngredient.type)) {
                    continue
                }

                val itemMeta = ingredient.itemMeta as? Damageable ?: continue
                itemMeta.damage += damageableIngredient.damage
                ingredient.itemMeta = itemMeta

                if (itemMeta.damage < ingredient.type.maxDurability) {
                    result[index] = ingredient
                } else {
                    result[index] = null
                }
            }
        }

        runTaskLater(1) {
            val matrix2 = inventory.matrix
            for ((index, itemStack) in result) {
                matrix2[index] = itemStack
            }
            inventory.matrix = matrix2
        }
    }
}