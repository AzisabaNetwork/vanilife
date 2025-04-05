package net.azisaba.vanilife.item.group

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.item.ItemGroup
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
object AllGroup: ItemGroup {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "all")

    override val icon: ItemStack = run {
        val itemStack = ItemStack.of(Material.NETHER_STAR)
        itemStack.itemMeta = itemStack.itemMeta.apply {
            addEnchant(Enchantment.INFINITY, 1, false)
            addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
        itemStack
    }

    override val title: Component = Component.translatable("itemGroup.vanilife.all")
}