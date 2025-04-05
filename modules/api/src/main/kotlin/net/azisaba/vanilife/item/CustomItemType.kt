package net.azisaba.vanilife.item

import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemType
import org.jetbrains.annotations.Range

interface CustomItemType: Keyed {
    val itemType: ItemType

    val itemModel: Key?
        get() = null

    val displayName: Component

    val lore: List<Component>
        get() = emptyList()

    val group: ItemGroup

    val rarity: ItemRarity
        get() = ItemRarity.COMMON

    val maxStackSize: @Range(from = 1, to = 99) Int
        get() = itemType.maxStackSize

    val enchantmentAura: Boolean
        get() = false

    fun onInHand(player: Player) {
    }

    fun onInMainHand(player: Player) {
    }

    fun onInOffHand(player: Player) {
    }

    fun use(player: Player) {
    }
}