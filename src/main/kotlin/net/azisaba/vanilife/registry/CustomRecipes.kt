package net.azisaba.vanilife.registry

import net.azisaba.vanilife.Vanilife.Companion.PLUGIN_ID
import net.azisaba.vanilife.extension.createEnchantedBook
import net.azisaba.vanilife.extension.getEnchantment
import net.azisaba.vanilife.extension.toNamespacedKey
import net.kyori.adventure.key.Key
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.ShapedRecipe

object CustomRecipes: KeyedRegistry<CraftingRecipe>() {
    val BULK_MINING_LV1_ENCHANTED_BOOK = register(ShapedRecipe(Key.key(PLUGIN_ID, "bulk_mining_lv1_enchanted_book").toNamespacedKey(), CustomEnchantments.BULK_MINING.getEnchantment().createEnchantedBook()).apply {
        shape("CCC", "CBC", "CCC")
        setIngredient('C', CustomItemTypes.CAVENIUM.createItemStack())
        setIngredient('B', CustomItemTypes.BULK_MINING_BOOK.createItemStack())
    })

    val BULK_MINING_LV2_ENCHANTED_BOOK = register(ShapedRecipe(Key.key(PLUGIN_ID, "bulk_mining_lv2_enchanted_book").toNamespacedKey(), CustomEnchantments.BULK_MINING.getEnchantment().createEnchantedBook(level = 2)).apply {
        shape("CCC", "CBC", "CCC")
        setIngredient('C', CustomItemTypes.COMPRESSED_CAVENIUM.createItemStack())
        setIngredient('B', CustomItemTypes.BULK_MINING_BOOK.createItemStack())
    })

    val MAGNITE_MINING_ENCHANTED_BOOK = register(ShapedRecipe(Key.key(PLUGIN_ID, "magnite_mining_enchanted_book").toNamespacedKey(), CustomEnchantments.MAGNITE_MINING.getEnchantment().createEnchantedBook()).apply {
        shape("MMM", "MBM", "MMM")
        setIngredient('M', CustomItemTypes.MAGNITE.createItemStack())
        setIngredient('B', CustomItemTypes.MAGNITE_MINING_BOOK.createItemStack())
    })

    val RANGE_MINING_LV1_ENCHANTED_BOOK = register(ShapedRecipe(Key.key(PLUGIN_ID, "range_mining_lv1_enchanted_book").toNamespacedKey(), CustomEnchantments.RANGE_MINING.getEnchantment().createEnchantedBook()).apply {
        shape("CCC", "CBC", "CCC")
        setIngredient('C', CustomItemTypes.CAVENIUM.createItemStack())
        setIngredient('B', CustomItemTypes.RANGE_MINING_BOOK.createItemStack())
    })

    val RANGE_MINING_LV2_ENCHANTED_BOOK = register(ShapedRecipe(Key.key(PLUGIN_ID, "range_mining_lv2_enchanted_book").toNamespacedKey(), CustomEnchantments.RANGE_MINING.getEnchantment().createEnchantedBook(level = 2)).apply {
        shape("CCC", "CBC", "CCC")
        setIngredient('C', CustomItemTypes.COMPRESSED_CAVENIUM.createItemStack())
        setIngredient('B', CustomItemTypes.RANGE_MINING_BOOK.createItemStack())
    })
}