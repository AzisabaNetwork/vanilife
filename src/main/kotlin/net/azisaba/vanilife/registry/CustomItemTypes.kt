package net.azisaba.vanilife.registry

import net.azisaba.vanilife.item.*
import org.bukkit.Bukkit

object CustomItemTypes: KeyedRegistry<CustomItemType>() {
    val MONEY_1000 = register(MoneyItemType.MONEY_1000)
    val MONEY_5000 = register(MoneyItemType.MONEY_5000)
    val MONEY_10000 = register(MoneyItemType.MONEY_10000)

    override fun <T : CustomItemType> register(value: T): T {
        value.craftingRecipes?.let {
            for (recipe in it) {
                Bukkit.addRecipe(recipe)
            }
        }

        return super.register(value)
    }
}