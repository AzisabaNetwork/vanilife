package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.toNamespacedKey
import net.azisaba.vanilife.registry.CustomItemTypes
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemType
import org.bukkit.inventory.ShapelessRecipe

object CompressedCaveniumItemType: CustomItemType {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "compressed_cavenium")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "cavenium")

    override val displayName: ComponentLike
        get() = Component.translatable("item.vanilife.compressed_cavenium")

    override val rarity: ItemRarity
        get() = ItemRarity.UNCOMMON

    override val enchantmentAura: Boolean
        get() = true

    override val craftingRecipes: List<CraftingRecipe>
        get() {
            return listOf(ShapelessRecipe(key.toNamespacedKey(), createItemStack()).apply {
                addIngredient(9, CustomItemTypes.CAVENIUM.createItemStack())
            })
        }
}