package net.azisaba.vanilife.item

import net.azisaba.vanilife.extension.ItemStack
import net.azisaba.vanilife.loot.modifier.LootModifier
import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

interface CustomItemType: Keyed {
    val itemType: ItemType

    val itemModel: Key?
        get() = null

    val displayName: Component

    val lore: List<Component>
        get() = emptyList()

    val rarity: ItemRarity
        get() = ItemRarity.COMMON

    val maxStackSize: Int
        get() = itemType.maxStackSize

    val enchantmentAura: Boolean
        get() = false

    val craftingRecipes: List<CraftingRecipe>?
        get() = null

    val lootModifiers: List<LootModifier>?
        get() = null

    fun onInHand(player: Player) {
    }

    fun onInMainHand(player: Player) {
    }

    fun onInOffHand(player: Player) {
    }

    fun use(player: Player) {
    }

    fun createItemStack(amount: Int = 1): ItemStack {
        return ItemStack(this, amount)
    }
}