package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.ItemStack
import net.azisaba.vanilife.extension.toNamespacedKey
import net.azisaba.vanilife.registry.ItemTypes
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.ItemType
import org.bukkit.inventory.ShapelessRecipe

object CompressedCaveniumItemType: CustomItemType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "compressed_cavenium")

    override val type: ItemType = ItemType.STICK

    override val title: Component = Component.translatable("item.vanilife.compressed_cavenium")

    override val aura: Boolean = true

    override val model: Key = Key.key(Vanilife.PLUGIN_ID, "cavenium")

    override val recipe: CraftingRecipe = ShapelessRecipe(key.toNamespacedKey(), ItemStack(this)).apply {
        addIngredient(9, ItemStack(ItemTypes.CAVENIUM))
    }
}