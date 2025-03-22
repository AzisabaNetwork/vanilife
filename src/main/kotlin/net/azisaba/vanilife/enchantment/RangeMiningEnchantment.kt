package net.azisaba.vanilife.enchantment

import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys
import io.papermc.paper.registry.tag.TagKey
import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

object RangeMiningEnchantment: ToolEnchantment {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "range_mining")

    override val displayName: ComponentLike
        get() = Component.translatable("enchantment.vanilife.range_mining")

    override val maximumLevel: Int
        get() = 2

    override val maximumCost: EnchantmentRegistryEntry.EnchantmentCost
        get() = EnchantmentRegistryEntry.EnchantmentCost.of(1, 1)

    override val minimumCost: EnchantmentRegistryEntry.EnchantmentCost
        get() = EnchantmentRegistryEntry.EnchantmentCost.of(1, 3)

    override val anvilCost: Int
        get() = 1

    override val supportedItems: TagKey<ItemType>
        get() = ItemTypeTagKeys.PICKAXES

    override fun use(player: Player, block: Block, itemStack: ItemStack, enchantment: Enchantment) {
        val range = when (itemStack.getEnchantmentLevel(enchantment)) {
            1 -> {
                block.getRelative(BlockFace.DOWN).breakNaturally(itemStack)
                return
            }
            else -> itemStack.getEnchantmentLevel(enchantment)
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