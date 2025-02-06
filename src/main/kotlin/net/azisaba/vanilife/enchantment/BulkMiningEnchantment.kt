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
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

object BulkMiningEnchantment: ToolEnchantment {
    private val ORES = mutableSetOf(Material.COAL_ORE,
        Material.DEEPSLATE_COAL_ORE,
        Material.IRON_ORE,
        Material.DEEPSLATE_IRON_ORE,
        Material.COPPER_ORE,
        Material.DEEPSLATE_COPPER_ORE,
        Material.GOLD_ORE,
        Material.NETHER_GOLD_ORE,
        Material.REDSTONE_ORE,
        Material.DEEPSLATE_REDSTONE_ORE,
        Material.EMERALD_ORE,
        Material.DEEPSLATE_EMERALD_ORE,
        Material.LAPIS_ORE,
        Material.DEEPSLATE_LAPIS_ORE,
        Material.DIAMOND_ORE,
        Material.DEEPSLATE_DIAMOND_ORE,
        Material.NETHER_GOLD_ORE,
        Material.NETHER_QUARTZ_ORE)

    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "bulk_mining")

    override val name: Component
        get() = Component.translatable("enchantment.${key.namespace()}.${key.value()}")

    override val maxLevel: Int
        get() = 2

    override val anvilCost: Int
        get() = 1

    override val minimumCost: EnchantmentRegistryEntry.EnchantmentCost
        get() = EnchantmentRegistryEntry.EnchantmentCost.of(1, 1)

    override val maximumCost: EnchantmentRegistryEntry.EnchantmentCost
        get() = EnchantmentRegistryEntry.EnchantmentCost.of(1, 3)

    override val exclusives: Set<Keyed>
        get() = setOf(Enchantments.RANGE_MINING, Enchantment.FORTUNE.key, Enchantment.SILK_TOUCH.key)

    override fun use(event: BlockBreakEvent) {
        val block = event.block.takeIf { ORES.contains(it.type) } ?: return

        fun mine(player: Player, itemStack: ItemStack, block: Block, source: Block, type: Material, level: Int) {
            block.breakNaturally(itemStack)

            for (face in BlockFace.entries) {
                val relative = block.getRelative(face)

                if (relative.type != type) {
                    continue
                }

                if (relative.location.distance(source.location) <= level) {
                    mine(player, itemStack, relative, source, type, level)
                }
            }
        }

        val player = event.player
        val itemStack = player.hand

        mine(player, itemStack, block, block, block.type, itemStack.enchantments.entries.first { it.key.key() == key }.value)
    }

    override fun createSupportedItems(event: RegistryFreezeEvent<*, *>): RegistryKeySet<ItemType> {
        return event.getOrCreateTag(ItemTypeTagKeys.PICKAXES)
    }
}