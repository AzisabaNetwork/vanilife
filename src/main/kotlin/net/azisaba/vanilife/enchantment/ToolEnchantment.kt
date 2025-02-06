package net.azisaba.vanilife.enchantment

import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.EquipmentSlotGroup

interface ToolEnchantment: Enchantment {
    override val activeSlots: Set<EquipmentSlotGroup>
        get() = setOf(EquipmentSlotGroup.HAND)

    fun use(event: BlockBreakEvent)
}