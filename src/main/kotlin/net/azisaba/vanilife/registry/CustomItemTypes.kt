package net.azisaba.vanilife.registry

import net.azisaba.vanilife.item.*
import org.bukkit.Bukkit

object CustomItemTypes: KeyedRegistry<CustomItemType>() {
    override fun <T : CustomItemType> register(value: T): T {
        value.craftingRecipes?.let {
            for (recipe in it) {
                Bukkit.addRecipe(recipe)
            }
        }

        return super.register(value)
    }
}