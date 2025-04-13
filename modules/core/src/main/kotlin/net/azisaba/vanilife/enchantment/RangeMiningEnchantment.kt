package net.azisaba.vanilife.enchantment

import com.tksimeji.gonunne.enchantment.CustomEnchantment
import com.tksimeji.gonunne.enchantment.effect.EnchantmentEffectEvent
import com.tksimeji.gonunne.enchantment.effect.EnchantmentEffects
import com.tksimeji.gonunne.enchantment.getEnchantmentLevel
import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys
import io.papermc.paper.registry.tag.TagKey
import net.azisaba.vanilife.PLUGIN_ID
import net.azisaba.vanilife.registry.CustomEnchantments
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemType

object RangeMiningEnchantment: CustomEnchantment {
    override val key: Key = Key.key(PLUGIN_ID, "range_mining")

    override val displayName: Component = Component.translatable("enchantment.vanilife.range_mining")

    override val maxLevel: Int = 2

    override val maxCost: EnchantmentRegistryEntry.EnchantmentCost = EnchantmentRegistryEntry.EnchantmentCost.of(1, 1)

    override val minCost: EnchantmentRegistryEntry.EnchantmentCost = EnchantmentRegistryEntry.EnchantmentCost.of(1, 3)

    override val anvilCost: Int = 8

    override val enchantingTableWeight: Int = 1

    override val activeSlots: Set<EquipmentSlotGroup> = setOf(EquipmentSlotGroup.MAINHAND)

    override val exclusiveSet: Set<Keyed> = setOf(CustomEnchantments.BULK_MINING)

    override val supportedItems: TagKey<ItemType> = ItemTypeTagKeys.PICKAXES

    override val effects = EnchantmentEffects.create()
        .add(EnchantmentEffectEvent.BREAK_BLOCK) { ctx ->
            val blocks = ctx.blocks
            val block = blocks.firstOrNull() ?: return@add
            val itemStack = ctx.itemStack

            val range = when (itemStack.getEnchantmentLevel(this)) {
                1 -> {
                    blocks.add(block.getRelative(BlockFace.DOWN))
                    return@add
                }
                else -> itemStack.getEnchantmentLevel(this)
            }

            val pos1 = Location(block.world,
                block.x - (if (range % 2 != 0) range / 2 else range - 1).toDouble(),
                block.y - (if (range % 2 != 0) range / 2 else range - 1).toDouble(),
                block.z - (if (range % 2 != 0) range / 2 else range - 1).toDouble())

            val pos2 = Location(block.world,
                block.x + (if (range % 2 != 0) range / 2 else 0).toDouble(),
                block.y + (if (range % 2 != 0) range / 2 else 0).toDouble(),
                block.z + (if (range % 2 != 0) range / 2 else 0).toDouble())

            for (x in pos1.blockX..pos2.blockX) {
                for (y in pos1.blockY..pos2.blockY) {
                    for (z in pos1.blockZ..pos2.blockZ) {
                        blocks.add(block.world.getBlockAt(x, y, z))
                    }
                }
            }
        }
}