package net.azisaba.vanilife.listener

import net.azisaba.vanilife.extension.RECIPE_EXTENSION_HANDLERS
import net.azisaba.vanilife.extension.RECIPE_EXTENSION_INGREDIENTS
import net.azisaba.vanilife.extension.customItemType
import net.azisaba.vanilife.extension.toItemType
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

        RECIPE_EXTENSION_HANDLERS[recipe.key()]?.let { it(event) }

        val inventory = event.inventory

        val triple = RECIPE_EXTENSION_INGREDIENTS.firstOrNull { it.first == recipe.key() } ?: return
        val type = triple.second
        val cost = triple.third

        val matrix1 = inventory.matrix.map { it?.clone() }.toTypedArray()
        val damagedLayerMap = mutableMapOf<Int, ItemStack?>()

        for ((index, ingredient) in matrix1.withIndex()) {
            if (ingredient == null || (ingredient.type.toItemType() != type && ingredient.customItemType != type)) {
                continue
            }

            val itemMeta = ingredient.itemMeta

            if (itemMeta !is Damageable) {
                continue
            }

            itemMeta.damage += cost
            ingredient.apply { this.itemMeta = itemMeta }

            if (itemMeta.damage < ingredient.type.maxDurability) {
                damagedLayerMap[index] = ingredient
            } else {
                damagedLayerMap[index] = null
            }
        }

        runTaskLater(1) {
            val matrix2 = inventory.matrix
            for ((index, itemStack) in damagedLayerMap) {
                matrix2[index] = itemStack
            }
            inventory.matrix = matrix2
        }
    }
}