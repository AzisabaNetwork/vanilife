package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.item.CustomItemType
import com.tksimeji.gonunne.item.Fruit
import com.tksimeji.gonunne.item.createItemStack
import com.tksimeji.gonunne.key.toNamespacedKey
import com.tksimeji.gonunne.recipe.DamageableShapelessRecipe
import com.tksimeji.gonunne.registry.impl.RegistryImpl
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

object CustomRecipes: RegistryImpl<Key, Recipe>() {
    val APPLE_JAM = register(ShapelessRecipe(Key.key(PLUGIN_ID, "apple_jam").toNamespacedKey(), CustomItemTypes.APPLE_JAM.createItemStack()).apply {
        addIngredient(Material.APPLE)
        addIngredient(Material.SUGAR)
        addIngredient(Material.GLASS_BOTTLE)
    })

    val BACON = register(ShapelessRecipe(Key.key(PLUGIN_ID, "bacon").toNamespacedKey(), CustomItemTypes.BACON.createItemStack()).apply {
        addIngredient(Material.PORKCHOP)
        addIngredient(CustomItemTypes.SALT.createItemStack())
    })

    val BUTTER = register(ShapelessRecipe(Key.key(PLUGIN_ID, "butter").toNamespacedKey(), CustomItemTypes.BUTTER.createItemStack()).apply {
        addIngredient(Material.MILK_BUCKET)
        addIngredient(CustomItemTypes.SALT.createItemStack())
    })

    val BUTTERED_POTATO = register(ShapelessRecipe(Key.key(PLUGIN_ID, "buttered_potato").toNamespacedKey(), CustomItemTypes.BUTTERED_POTATO.createItemStack()).apply {
        addIngredient(Material.BAKED_POTATO)
        addIngredient(CustomItemTypes.SALT.createItemStack())
        addIngredient(CustomItemTypes.BUTTER.createItemStack())
    })
    
    val CHEESE = register(ShapelessRecipe(Key.key(PLUGIN_ID, "cheese").toNamespacedKey(), CustomItemTypes.CHEESE.createItemStack()).apply {
        addIngredient(Material.MILK_BUCKET)
        addIngredient(CustomItemTypes.ROCK_SALT.createItemStack())
    })

    val DOUGH = register(ShapelessRecipe(Key.key(PLUGIN_ID, "dough").toNamespacedKey(), CustomItemTypes.DOUGH.createItemStack()).apply {
        addIngredient(Material.WATER_BUCKET)
        addIngredient(CustomItemTypes.SALT.createItemStack())
        addIngredient(CustomItemTypes.WHEAT_FLOUR.createItemStack())
    })

    val FIREPROOF_REEL = register(ShapedRecipe(Key.key(PLUGIN_ID, "fireproof_reel").toNamespacedKey(), CustomItemTypes.FIREPROOF_REEL.createItemStack()).apply {
        shape("MIM", "MSM", "MMM")
        setIngredient('M', CustomItemTypes.REFINED_MAGMA_CREAM.createItemStack())
        setIngredient('I', Material.IRON_INGOT)
        setIngredient('S', Material.STRING)
    })

    val FRUIT_SANDWICH = registerSandwichRecipes("sruit_sandwich", CustomItemTypes.FRUIT_SANDWICH, CustomItemTypes.filterIsInstance<Fruit>())

    val KNIFE = register(ShapedRecipe(Key.key(PLUGIN_ID, "knife").toNamespacedKey(), CustomItemTypes.KNIFE.createItemStack()).apply {
        shape("I", "S")
        setIngredient('I', Material.IRON_INGOT)
        setIngredient('S', Material.STICK)
    })

    val MAYONNAISE = register(ShapelessRecipe(Key.key(PLUGIN_ID, "mayonnaise").toNamespacedKey(), CustomItemTypes.MAYONNAISE.createItemStack()).apply {
        addIngredient(Material.EGG)
        addIngredient(Material.GLASS_BOTTLE)
    })

    val SALT = register(ShapedRecipe(Key.key(PLUGIN_ID, "salt").toNamespacedKey(), CustomItemTypes.SALT.createItemStack()).apply {
        shape("RR", "RR")
        setIngredient('R', CustomItemTypes.ROCK_SALT.createItemStack())
    })

    val SANDWICH = registerSandwichRecipes("sandwich", CustomItemTypes.SANDWICH, listOf(CustomItemTypes.BACON, CustomItemTypes.LETTUCE, CustomItemTypes.SLICED_TOMATO))

    val SLICED_TOMATO = register(DamageableShapelessRecipe(Key.key(PLUGIN_ID, "sliced_tomato").toNamespacedKey(), CustomItemTypes.SLICED_TOMATO.createItemStack(3)).apply {
        addIngredient(CustomItemTypes.TOMATO.createItemStack())
        addDamageableIngredient(CustomItemTypes.KNIFE, 4)
    })

    val WHEAT_FLOUR = register(DamageableShapelessRecipe(Key.key(PLUGIN_ID, "wheat_flour").toNamespacedKey(), CustomItemTypes.WHEAT_FLOUR.createItemStack()).apply {
        addIngredient(Material.WHEAT)
        addDamageableIngredient(CustomItemTypes.KNIFE, 10)
    })

    val WHITE_TOAST = register(ShapelessRecipe(Key.key(PLUGIN_ID, "white_bread").toNamespacedKey(), CustomItemTypes.WHITE_BREAD.createItemStack()).apply {
        addIngredient(Material.SUGAR)
        addIngredient(CustomItemTypes.DOUGH.createItemStack())
        addIngredient(CustomItemTypes.YEAST.createItemStack())
    })

    val YEAST = register(ShapelessRecipe(Key.key(PLUGIN_ID, "yeast").toNamespacedKey(), CustomItemTypes.YEAST.createItemStack()).apply {
        addIngredient(Material.BROWN_MUSHROOM)
        addIngredient(Material.SUGAR)
        addIngredient(Material.WHEAT)
    })

    private fun <T: CraftingRecipe> register(craftingRecipe: T): T {
        return register(craftingRecipe.key, craftingRecipe)
    }

    private fun registerSandwichRecipes(name: String, result: CustomItemType, fillings: List<CustomItemType>): List<ShapedRecipe> {
        if (fillings.size < 3) {
            throw IllegalArgumentException("Filling must be 3 or more.")
        }

        val recipes = mutableListOf<ShapedRecipe>()
        val size = fillings.size

        for (i in 0 until size) {
            for (j in 0 until size) {
                if (i == j) {
                    continue
                }

                for (k in 0 until size) {
                    if (k == i || k == j) {
                        continue
                    }

                    val filling1 = fillings[i]
                    val filling2 = fillings[j]
                    val filling3 = fillings[k]

                    val key = Key.key(PLUGIN_ID, "${name}_${filling1.key().value()}_${filling2.key().value()}_${filling3.key().value()}")
                    val recipe = ShapedRecipe(key.toNamespacedKey(), result.createItemStack()).apply {
                        shape("BBB", "XYZ", "BBB")
                        setIngredient('B', CustomItemTypes.WHITE_BREAD.createItemStack())
                        setIngredient('X', filling1.createItemStack())
                        setIngredient('Y', filling2.createItemStack())
                        setIngredient('Z', filling3.createItemStack())
                    }

                    register(recipe)
                    recipes.add(recipe)
                }
            }
        }

        return recipes
    }
}