package net.azisaba.vanilife.enchantment

import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys
import io.papermc.paper.registry.tag.TagKey
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.toBlockType
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockType
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import kotlin.random.Random

object MagniteMiningEnchantment: ToolEnchantment {
    private val MODIFY_MAP = mutableMapOf(Pair(BlockType.IRON_ORE, ItemType.IRON_INGOT),
        Pair(BlockType.DEEPSLATE_IRON_ORE, ItemType.IRON_INGOT),
        Pair(BlockType.COPPER_ORE, ItemType.COPPER_INGOT),
        Pair(BlockType.DEEPSLATE_COPPER_ORE, ItemType.COPPER_INGOT),
        Pair(BlockType.GOLD_ORE, ItemType.GOLD_INGOT),
        Pair(BlockType.DEEPSLATE_GOLD_ORE, ItemType.GOLD_INGOT))

    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "magnite_mining")

    override val displayName: Component
        get() = Component.translatable("enchantment.vanilife.magnite_mining")

    override val maximumCost: EnchantmentRegistryEntry.EnchantmentCost
        get() = EnchantmentRegistryEntry.EnchantmentCost.of(1, 1)

    override val minimumCost: EnchantmentRegistryEntry.EnchantmentCost
        get() = EnchantmentRegistryEntry.EnchantmentCost.of(1, 3)

    override val anvilCost: Int
        get() = 1

    override val supportedItems: TagKey<ItemType>
        get() = ItemTypeTagKeys.PICKAXES

    override val exclusives: Set<Keyed>
        get() = setOf(Enchantment.SILK_TOUCH)

    override val usePriority: Int
        get() = Int.MAX_VALUE

    override fun use(player: Player, blocks: MutableSet<Block>, itemStack: ItemStack, enchantment: Enchantment) {
        var modified = 0

        for (block in blocks.toSet()) {
            val blockType = block.type.toBlockType()
            if (MODIFY_MAP.containsKey(blockType)) {
                blocks.remove(block)
                block.type = Material.AIR
                block.world.dropItemNaturally(block.location, MODIFY_MAP[blockType]!!.createItemStack())
                modified++
            }
        }

        if (modified > 0 && Random.nextDouble() < 0.25) {
            player.world.playSound(player.location, Sound.BLOCK_FIRE_EXTINGUISH, 1.0F, 0.2F)
        }
    }
}