package net.azisaba.vanilife.enchantment

import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys
import io.papermc.paper.registry.tag.TagKey
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.level
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

object RangeMiningEnchantment: ToolEnchantment {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "range_mining")

    override val maxLevel: Int = 2

    override val maximumCost: EnchantmentRegistryEntry.EnchantmentCost = EnchantmentRegistryEntry.EnchantmentCost.of(1, 1)

    override val minimumCost: EnchantmentRegistryEntry.EnchantmentCost = EnchantmentRegistryEntry.EnchantmentCost.of(1, 3)

    override val anvilCost: Int = 1

    override val supportedItems: TagKey<ItemType> = ItemTypeTagKeys.PICKAXES

    override val exclusives: Set<Keyed> = setOf(BulkMiningEnchantment, Enchantment.SILK_TOUCH)

    override fun use(player: Player, block: Block, itemStack: ItemStack, enchantment: Enchantment) {
        val range = when (enchantment.level(itemStack)) {
            1 -> {
                block.getRelative(BlockFace.DOWN).breakNaturally(itemStack)
                return
            }
            else -> enchantment.level(itemStack)
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
                    block.world.getBlockAt(x, y, z).breakNaturally(itemStack)
                }
            }
        }
    }
}