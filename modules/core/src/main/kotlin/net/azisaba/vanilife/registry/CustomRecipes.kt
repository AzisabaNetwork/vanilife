package net.azisaba.vanilife.registry

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.*
import net.azisaba.vanilife.item.CustomItemType
import net.azisaba.vanilife.item.Fruit
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.ItemType
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

object CustomRecipes: KeyedRegistry<CraftingRecipe>() {
    val APPLE_JAM = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "apple_jam").toNamespacedKey(), CustomItemTypes.APPLE_JAM.createItemStack()).apply {
        addIngredient(ItemType.APPLE.createItemStack())
        addIngredient(ItemType.SUGAR.createItemStack())
        addIngredient(ItemType.GLASS_BOTTLE.createItemStack())
    })

    val BACON = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "bacon").toNamespacedKey(), CustomItemTypes.BACON.createItemStack()).apply {
        addIngredient(Material.PORKCHOP)
        addIngredient(CustomItemTypes.SALT.createItemStack())
    })

    val BUTTER = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "butter").toNamespacedKey(), CustomItemTypes.BUTTER.createItemStack()).apply {
        addIngredient(Material.MILK_BUCKET)
        addIngredient(CustomItemTypes.SALT.createItemStack())
    })

    val BUTTERED_POTATO = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "buttered_potato").toNamespacedKey(), CustomItemTypes.BUTTERED_POTATO.createItemStack()).apply {
        addIngredient(Material.BAKED_POTATO)
        addIngredient(CustomItemTypes.SALT.createItemStack())
        addIngredient(CustomItemTypes.BUTTER.createItemStack())
    })
    
    val CHEESE = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "cheese").toNamespacedKey(), CustomItemTypes.CHEESE.createItemStack()).apply {
        addIngredient(Material.MILK_BUCKET)
        addIngredient(CustomItemTypes.ROCK_SALT.createItemStack())
    })

    val DOUGH = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "dough").toNamespacedKey(), CustomItemTypes.DOUGH.createItemStack()).apply {
        addIngredient(Material.WATER_BUCKET)
        addIngredient(CustomItemTypes.SALT.createItemStack())
        addIngredient(CustomItemTypes.WHEAT_FLOUR.createItemStack())
    })

    val FRUIT_SANDWICH = createSandwichRecipes("sruit_sandwich", CustomItemTypes.FRUIT_SANDWICH, CustomItemTypes.filter { it is Fruit })

    val KNIFE = register(ShapedRecipe(Key.key(Vanilife.PLUGIN_ID, "knife").toNamespacedKey(), CustomItemTypes.KNIFE.createItemStack()).apply {
        shape("I", "S")
        setIngredient('I', ItemType.IRON_INGOT.createItemStack())
        setIngredient('S', ItemType.STICK.createItemStack())
    })

    val MAYONNAISE = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "mayonnaise").toNamespacedKey(), CustomItemTypes.MAYONNAISE.createItemStack()).apply {
        addIngredient(ItemType.EGG.createItemStack())
        addIngredient(ItemType.GLASS_BOTTLE.createItemStack())
    })

    val SALT = register(ShapedRecipe(Key.key(Vanilife.PLUGIN_ID, "salt").toNamespacedKey(), CustomItemTypes.SALT.createItemStack()).apply {
        shape("RR", "RR")
        setIngredient('R', CustomItemTypes.ROCK_SALT.createItemStack())
    })

    val SANDWICH = createSandwichRecipes("sandwich", CustomItemTypes.SANDWICH, listOf(CustomItemTypes.BACON, CustomItemTypes.LETTUCE, CustomItemTypes.SLICED_TOMATO))

    val SLICED_TOMATO = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "sliced_tomato").toNamespacedKey(), CustomItemTypes.SLICED_TOMATO.createItemStack(3)).apply {
        addIngredient(CustomItemTypes.TOMATO.createItemStack())
        addDamageableIngredient(CustomItemTypes.KNIFE, 4)
    })

    val WHEAT_FLOUR = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "wheat_flour").toNamespacedKey(), CustomItemTypes.WHEAT_FLOUR.createItemStack()).apply {
        addIngredient(ItemType.WHEAT.createItemStack())
        addDamageableIngredient(CustomItemTypes.KNIFE, 10)
        setCraftSound(Sound.UI_STONECUTTER_TAKE_RESULT, pitch = 0.1F)
    })

    val WHITE_TOAST = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "white_bread").toNamespacedKey(), CustomItemTypes.WHITE_BREAD.createItemStack()).apply {
        addIngredient(Material.SUGAR)
        addIngredient(CustomItemTypes.DOUGH.createItemStack())
        addIngredient(CustomItemTypes.YEAST.createItemStack())
    })

    val YEAST = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "yeast").toNamespacedKey(), CustomItemTypes.YEAST.createItemStack()).apply {
        addIngredient(Material.BROWN_MUSHROOM)
        addIngredient(Material.SUGAR)
        addIngredient(Material.WHEAT)
    })

    private fun createSandwichRecipes(name: String, result: CustomItemType, fillings: List<CustomItemType>): List<ShapedRecipe> {
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

                    val key = Key.key(Vanilife.PLUGIN_ID, "${name}_${filling1.key().value()}_${filling2.key().value()}_${filling3.key().value()}")
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