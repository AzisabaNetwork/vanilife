package net.azisaba.vanilife.enchantment

import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.event.RegistryFreezeEvent
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys
import io.papermc.paper.registry.set.RegistryKeySet
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.util.hand
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemType

object RangeMiningEnchantment: ToolEnchantment {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "range_mining")

    override val name: Component
        get() = Component.translatable("enchantment.${key.namespace()}.${key.value()}")

    override val maxLevel: Int
        get() = 3

    override val anvilCost: Int
        get() = 1

    override val minimumCost: EnchantmentRegistryEntry.EnchantmentCost
        get() = EnchantmentRegistryEntry.EnchantmentCost.of(1, 1)

    override val maximumCost: EnchantmentRegistryEntry.EnchantmentCost
        get() = EnchantmentRegistryEntry.EnchantmentCost.of(1, 3)

    override val exclusives: Set<Keyed>
        get() = setOf(Enchantments.BULK_MINING, Enchantment.FORTUNE.key)

    override fun use(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        val itemStack = player.hand

        val enchantment = itemStack.enchantments.keys
            .firstOrNull { it.key().key() == key } ?: return
        val level = itemStack.enchantments[enchantment]!!

        val range = when (level) {
            1 -> {
                block.getRelative(BlockFace.DOWN).breakNaturally(itemStack)
                return
            }
            2 -> 2
            3 -> 3
            else -> throw UnsupportedOperationException()
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

    override fun createSupportedItems(event: RegistryFreezeEvent<*, *>): RegistryKeySet<ItemType> {
        return event.getOrCreateTag(ItemTypeTagKeys.PICKAXES)
    }
}