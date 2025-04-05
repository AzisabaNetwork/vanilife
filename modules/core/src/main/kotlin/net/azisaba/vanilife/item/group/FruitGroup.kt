package net.azisaba.vanilife.item.group

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.createItemStack
import net.azisaba.vanilife.item.ItemGroup
import net.azisaba.vanilife.registry.CustomItemTypes
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack

object FruitGroup: ItemGroup {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "fruit")

    override val icon: ItemStack by lazy { CustomItemTypes.STRAWBERRY.createItemStack() }

    override val title: Component = Component.translatable("itemGroup.vanilife.fruit")
}