package net.azisaba.vanilife.registry

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.*
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

    val BULK_MINING_LV1_ENCHANTED_BOOK = register(ShapedRecipe(Key.key(Vanilife.PLUGIN_ID, "bulk_mining_lv1_enchanted_book").toNamespacedKey(), CustomEnchantments.BULK_MINING.getEnchantment().createEnchantedBook()).apply {
        shape("CCC", "CBC", "CCC")
        setIngredient('C', CustomItemTypes.CAVENIUM.createItemStack())
        setIngredient('B', CustomItemTypes.BULK_MINING_BOOK.createItemStack())
    })

    val BULK_MINING_LV2_ENCHANTED_BOOK = register(ShapedRecipe(Key.key(Vanilife.PLUGIN_ID, "bulk_mining_lv2_enchanted_book").toNamespacedKey(), CustomEnchantments.BULK_MINING.getEnchantment().createEnchantedBook(level = 2)).apply {
        shape("CCC", "CBC", "CCC")
        setIngredient('C', CustomItemTypes.COMPRESSED_CAVENIUM.createItemStack())
        setIngredient('B', CustomItemTypes.BULK_MINING_BOOK.createItemStack())
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

    val COMPRESSED_CAVENIUM = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "compressed_cavenium").toNamespacedKey(), CustomItemTypes.COMPRESSED_CAVENIUM.createItemStack()).apply {
        addIngredient(9, CustomItemTypes.CAVENIUM.createItemStack())
    })

    val DOUGH = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "dough").toNamespacedKey(), CustomItemTypes.DOUGH.createItemStack()).apply {
        addIngredient(Material.WATER_BUCKET)
        addIngredient(CustomItemTypes.SALT.createItemStack())
        addIngredient(CustomItemTypes.WHEAT_FLOUR.createItemStack())
    })

    val KNIFE = register(ShapedRecipe(Key.key(Vanilife.PLUGIN_ID, "knife").toNamespacedKey(), CustomItemTypes.KNIFE.createItemStack()).apply {
        shape("I", "S")
        setIngredient('I', ItemType.IRON_INGOT.createItemStack())
        setIngredient('S', ItemType.STICK.createItemStack())
    })

    val MAGNITE_MINING_ENCHANTED_BOOK = register(ShapedRecipe(Key.key(Vanilife.PLUGIN_ID, "magnite_mining_enchanted_book").toNamespacedKey(), CustomEnchantments.MAGNITE_MINING.getEnchantment().createEnchantedBook()).apply {
        shape("MMM", "MBM", "MMM")
        setIngredient('M', CustomItemTypes.MAGNITE.createItemStack())
        setIngredient('B', CustomItemTypes.MAGNITE_MINING_BOOK.createItemStack())
    })

    val MAYONNAISE = register(ShapelessRecipe(Key.key(Vanilife.PLUGIN_ID, "mayonnaise").toNamespacedKey(), CustomItemTypes.MAYONNAISE.createItemStack()).apply {
        addIngredient(ItemType.EGG.createItemStack())
        addIngredient(ItemType.GLASS_BOTTLE.createItemStack())
    })

    val RANGE_MINING_LV1_ENCHANTED_BOOK = register(ShapedRecipe(Key.key(Vanilife.PLUGIN_ID, "range_mining_lv1_enchanted_book").toNamespacedKey(), CustomEnchantments.RANGE_MINING.getEnchantment().createEnchantedBook()).apply {
        shape("CCC", "CBC", "CCC")
        setIngredient('C', CustomItemTypes.CAVENIUM.createItemStack())
        setIngredient('B', CustomItemTypes.RANGE_MINING_BOOK.createItemStack())
    })

    val RANGE_MINING_LV2_ENCHANTED_BOOK = register(ShapedRecipe(Key.key(Vanilife.PLUGIN_ID, "range_mining_lv2_enchanted_book").toNamespacedKey(), CustomEnchantments.RANGE_MINING.getEnchantment().createEnchantedBook(level = 2)).apply {
        shape("CCC", "CBC", "CCC")
        setIngredient('C', CustomItemTypes.COMPRESSED_CAVENIUM.createItemStack())
        setIngredient('B', CustomItemTypes.RANGE_MINING_BOOK.createItemStack())
    })

    val SALT = register(ShapedRecipe(Key.key(Vanilife.PLUGIN_ID, "salt").toNamespacedKey(), CustomItemTypes.SALT.createItemStack()).apply {
        shape("RR", "RR")
        setIngredient('R', CustomItemTypes.ROCK_SALT.createItemStack())
    })

    val SANDWICH = register(ShapedRecipe(Key.key(Vanilife.PLUGIN_ID, "sandwich").toNamespacedKey(), CustomItemTypes.SANDWICH.createItemStack()).apply {
        shape("WWW", "BLT", "WWW")
        setIngredient('W', CustomItemTypes.WHITE_BREAD.createItemStack())
        setIngredient('B', CustomItemTypes.BACON.createItemStack())
        setIngredient('L', CustomItemTypes.LETTUCE.createItemStack())
        setIngredient('T', CustomItemTypes.TOMATO.createItemStack())
    })

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
}