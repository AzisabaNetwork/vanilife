package net.azisaba.vanilife.enchantment

import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys
import io.papermc.paper.registry.tag.TagKey
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.level
import net.azisaba.vanilife.extension.toMaterial
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.BlockType
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

object BulkMiningEnchantment: ToolEnchantment {
    private val oreBlockTypes = mutableSetOf(BlockType.COAL_ORE,
        BlockType.DEEPSLATE_COAL_ORE,
        BlockType.IRON_ORE,
        BlockType.DEEPSLATE_IRON_ORE,
        BlockType.COPPER_ORE,
        BlockType.DEEPSLATE_COPPER_ORE,
        BlockType.GOLD_ORE,
        BlockType.DEEPSLATE_GOLD_ORE,
        BlockType.REDSTONE_ORE,
        BlockType.DEEPSLATE_REDSTONE_ORE,
        BlockType.EMERALD_ORE,
        BlockType.DEEPSLATE_EMERALD_ORE,
        BlockType.LAPIS_ORE,
        BlockType.DEEPSLATE_LAPIS_ORE,
        BlockType.DIAMOND_ORE,
        BlockType.DEEPSLATE_DIAMOND_ORE,
        BlockType.NETHER_GOLD_ORE,
        BlockType.NETHER_QUARTZ_ORE)

    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "bulk_mining")

    override val maxLevel: Int = 2

    override val maximumCost: EnchantmentRegistryEntry.EnchantmentCost = EnchantmentRegistryEntry.EnchantmentCost.of(1, 1)

    override val minimumCost: EnchantmentRegistryEntry.EnchantmentCost = EnchantmentRegistryEntry.EnchantmentCost.of(1, 3)

    override val anvilCost: Int = 1

    override val activeSlots: Set<EquipmentSlotGroup>
        get() = setOf(EquipmentSlotGroup.HAND)

    override val supportedItems: TagKey<ItemType>
        get() = ItemTypeTagKeys.PICKAXES

    override val exclusives: Set<Keyed> = setOf(RangeMiningEnchantment, Enchantment.FORTUNE)

    override fun use(player: Player, block: Block, itemStack: ItemStack, enchantment: Enchantment) {
        if (! oreBlockTypes.map { it.toMaterial() }.contains(block.type)) {
            return
        }

        fun mine(block: Block, source: Block, type: Material = source.type) {
            block.breakNaturally(itemStack)

            for (face in BlockFace.entries) {
                val relativeBlock = block.getRelative(face)

                if (relativeBlock.type != type) {
                    continue
                }

                if (relativeBlock.location.distance(source.location) <= enchantment.level(itemStack)) {
                    mine(relativeBlock, source, type)
                }
            }
        }

        mine(block, block)
    }
}