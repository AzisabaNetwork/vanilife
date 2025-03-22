package net.azisaba.vanilife.item

import net.azisaba.vanilife.extension.ItemStack
import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.ComponentLike
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

interface CustomItemType: Keyed {
    val itemType: ItemType

    val itemModel: Key?
        get() = null

    val displayName: ComponentLike

    val lore: Collection<ComponentLike>
        get() = emptyList()

    val rarity: ItemRarity
        get() = ItemRarity.COMMON

    val maxStackSize: Int
        get() = itemType.maxStackSize

    val enchantmentAura: Boolean
        get() = false

    val craftingRecipes: List<CraftingRecipe>?
        get() = null

    fun createItemStack(amount: Int = 1): ItemStack {
        return ItemStack(this, amount)
    }
}