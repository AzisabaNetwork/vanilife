package net.azisaba.vanilife.enchantment

import com.tksimeji.gonunne.enchantment.CustomEnchantment
import com.tksimeji.gonunne.enchantment.context.BlockBreakContext
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
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemType

object BulkMiningEnchantment: CustomEnchantment {
    override val key: Key = Key.key(PLUGIN_ID, "bulk_mining")

    override val displayName: Component = Component.translatable("enchantment.vanilife.bulk_mining")

    override val maxLevel: Int = 2

    override val maxCost: EnchantmentRegistryEntry.EnchantmentCost = EnchantmentRegistryEntry.EnchantmentCost.of(1, 1)

    override val minCost: EnchantmentRegistryEntry.EnchantmentCost = EnchantmentRegistryEntry.EnchantmentCost.of(1, 3)

    override val anvilCost: Int = 8

    override val enchantingTableWeight: Int = 1

    override val activeSlots: Set<EquipmentSlotGroup> = setOf(EquipmentSlotGroup.MAINHAND)

    override val exclusiveSet: Set<Keyed> = setOf(CustomEnchantments.RANGE_MINING)

    override val supportedItems: TagKey<ItemType> = ItemTypeTagKeys.PICKAXES

    override val effects: EnchantmentEffects = EnchantmentEffects.create()
        .add(EnchantmentEffectEvent.BREAK_BLOCK) { ctx ->
            val block = ctx.blocks.firstOrNull() ?: return@add
            if (block.type != Material.IRON_ORE) {
                return@add
            }
            /* if (!CustomTags.ORE.map { it.toMaterial() }.contains(block.type)) {
                return@add
            } */
            breakBlock(block.type, block, block, ctx)
        }

    private fun breakBlock(type: Material, block: Block, source: Block, context: BlockBreakContext) {
        context.blocks.add(block)

        for (face in BlockFace.entries) {
            val relativeBlock = block.getRelative(face)
            if (relativeBlock.type != type) continue

            if (relativeBlock.location.distance(source.location) <= context.itemStack.getEnchantmentLevel(this)) {
                breakBlock(type, relativeBlock, source, context)
            }
        }
    }
}