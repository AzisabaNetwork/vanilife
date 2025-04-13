package com.tksimeji.gonunne.enchantment.context.impl

import com.tksimeji.gonunne.enchantment.context.BlockBreakContext
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

internal class BlockBreakContextImpl(override val player: Player, override val itemStack: ItemStack, override val equipmentSlot: EquipmentSlot, override val blocks: MutableSet<Block>): BlockBreakContext {
}