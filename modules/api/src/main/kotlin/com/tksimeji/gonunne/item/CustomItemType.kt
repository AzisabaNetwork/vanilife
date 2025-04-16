package com.tksimeji.gonunne.item

import com.tksimeji.gonunne.key.Keyed
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
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

    fun onInHand(player: Player) {
    }

    fun onInMainHand(player: Player) {
    }

    fun onInOffHand(player: Player) {
    }

    fun onPickup(itemStack: ItemStack, player: Player) {
    }

    fun use(itemStack: ItemStack, player: Player, action: Action, block: Block?, face: BlockFace) {
    }
}