package net.azisaba.vanilife.enchantment

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack

interface ToolEnchantment: CustomEnchantment {
    override val activeSlots: Set<EquipmentSlotGroup>
        get() = setOf(EquipmentSlotGroup.HAND)

    val usePriority: Int
        get() = -1

    fun use(player: Player, blocks: MutableSet<Block>, itemStack: ItemStack, enchantment: org.bukkit.enchantments.Enchantment)
}