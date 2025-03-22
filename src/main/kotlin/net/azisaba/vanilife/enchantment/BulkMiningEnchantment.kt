package net.azisaba.vanilife.enchantment

import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys
import io.papermc.paper.registry.tag.TagKey
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.toMaterial
import net.azisaba.vanilife.registry.CustomTags
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

object BulkMiningEnchantment: ToolEnchantment {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "bulk_mining")

    override val displayName: ComponentLike
        get() = Component.translatable("enchantment.vanilife.bulk_mining")

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

    override val exclusives: Set<Keyed>
        get() = setOf(RangeMiningEnchantment)

    override fun use(player: Player, block: Block, itemStack: ItemStack, enchantment: Enchantment) {
        if (!CustomTags.ORE.map { it.toMaterial() }.contains(block.type)) {
            return
        }

        fun mine(block: Block, source: Block, type: Material = source.type) {
            block.breakNaturally(itemStack)

            for (face in BlockFace.entries) {
                val relativeBlock = block.getRelative(face)

                if (relativeBlock.type != type) {
                    continue
                }

                if (relativeBlock.location.distance(source.location) <= itemStack.getEnchantmentLevel(enchantment)) {
                    mine(relativeBlock, source, type)
                }
            }
        }

        mine(block, block)
    }
}