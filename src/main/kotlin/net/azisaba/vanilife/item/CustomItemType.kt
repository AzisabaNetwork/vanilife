package net.azisaba.vanilife.item

import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.CraftingRecipe
import org.bukkit.inventory.ItemType

interface CustomItemType: Keyed {
    val type: ItemType

    val title: Component

    val lore: Collection<Component>
        get() = emptyList()

    val aura: Boolean
        get() = false

    val model: Key?
        get() = null

    val recipe: CraftingRecipe?
        get() = null
}