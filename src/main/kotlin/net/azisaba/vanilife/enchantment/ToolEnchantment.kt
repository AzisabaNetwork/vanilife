package net.azisaba.vanilife.enchantment

import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack

interface ToolEnchantment: CustomEnchantment {
    override val activeSlots: Set<EquipmentSlotGroup>
        get() = setOf(EquipmentSlotGroup.HAND)

    fun use(player: Player, block: Block, itemStack: ItemStack, enchantment: org.bukkit.enchantments.Enchantment)
}