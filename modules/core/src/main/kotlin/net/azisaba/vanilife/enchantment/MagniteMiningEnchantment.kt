package net.azisaba.vanilife.enchantment

import com.tksimeji.gonunne.enchantment.CustomEnchantment
import com.tksimeji.gonunne.enchantment.effect.EnchantmentEffectEvent
import com.tksimeji.gonunne.enchantment.effect.EnchantmentEffects
import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys
import io.papermc.paper.registry.tag.TagKey
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.EquipmentSlotGroup
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import kotlin.random.Random

object MagniteMiningEnchantment: CustomEnchantment {
    private val MODIFY_MAP = mapOf(
        Pair(Material.IRON_ORE, Material.IRON_INGOT),
        Pair(Material.DEEPSLATE_IRON_ORE, Material.IRON_INGOT),
        Pair(Material.COPPER_ORE, Material.COPPER_INGOT),
        Pair(Material.DEEPSLATE_COPPER_ORE, Material.COPPER_INGOT),
        Pair(Material.GOLD_ORE, Material.GOLD_INGOT),
        Pair(Material.DEEPSLATE_GOLD_ORE, Material.GOLD_INGOT)
    )

    override val key: Key = Key.key(PLUGIN_ID, "magnite_mining")

    override val displayName: Component = Component.translatable("enchantment.vanilife.magnite_mining")

    override val maxLevel: Int = 1

    override val maxCost: EnchantmentRegistryEntry.EnchantmentCost = EnchantmentRegistryEntry.EnchantmentCost.of(1, 1)

    override val minCost: EnchantmentRegistryEntry.EnchantmentCost = EnchantmentRegistryEntry.EnchantmentCost.of(1, 3)

    override val anvilCost: Int = 8

    override val enchantingTableWeight: Int = 1

    override val activeSlots: Set<EquipmentSlotGroup> = setOf(EquipmentSlotGroup.MAINHAND)

    override val exclusiveSet: Set<Keyed> = setOf(Enchantment.SILK_TOUCH)

    override val supportedItems: TagKey<ItemType> = ItemTypeTagKeys.PICKAXES

    override val effects: EnchantmentEffects = EnchantmentEffects.create()
        .add(EnchantmentEffectEvent.BREAK_BLOCK, Int.MAX_VALUE) { ctx ->
            val blocks = ctx.blocks
            var modified = 0

            for (block in blocks) {
                val blockType = block.type
                if (MODIFY_MAP.containsKey(blockType)) {
                    blocks.remove(block)
                    block.type = Material.AIR
                    block.world.dropItemNaturally(block.location, ItemStack.of(MODIFY_MAP[blockType]!!))
                    modified++
                }
            }

            if (modified > 0 && Random.nextFloat() < 0.25) {
                val player = ctx.player
                player.world.playSound(player.location, Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.2f)
            }
        }
}