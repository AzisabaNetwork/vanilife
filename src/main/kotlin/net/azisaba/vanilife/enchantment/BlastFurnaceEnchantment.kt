package net.azisaba.vanilife.enchantment

import io.papermc.paper.registry.data.EnchantmentRegistryEntry
import io.papermc.paper.registry.event.RegistryFreezeEvent
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys
import io.papermc.paper.registry.set.RegistryKeySet
import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import net.kyori.adventure.text.Component
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

object BlastFurnaceEnchantment: ToolEnchantment {
    private val map = mapOf(Pair(Material.IRON_ORE, Material.IRON_INGOT),
        Pair(Material.DEEPSLATE_IRON_ORE, Material.IRON_INGOT),
        Pair(Material.COPPER_ORE, Material.COPPER_INGOT),
        Pair(Material.DEEPSLATE_COPPER_ORE, Material.COPPER_INGOT),
        Pair(Material.GOLD_ORE, Material.GOLD_INGOT),
        Pair(Material.DEEPSLATE_GOLD_ORE, Material.GOLD_INGOT))

    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "blast_furnace")

    override val name: Component
        get() = Component.translatable("enchantment.${key.namespace()}.${key.value()}")

    override val anvilCost: Int
        get() = 1

    override val minimumCost: EnchantmentRegistryEntry.EnchantmentCost
        get() = EnchantmentRegistryEntry.EnchantmentCost.of(1, 1)

    override val maximumCost: EnchantmentRegistryEntry.EnchantmentCost
        get() = EnchantmentRegistryEntry.EnchantmentCost.of(1, 2)

    override val exclusives: Set<Keyed>
        get() = setOf(Enchantments.BULK_MINING, Enchantments.RANGE_MINING, Enchantment.FORTUNE.key, Enchantment.SILK_TOUCH.key)

    override fun use(event: BlockBreakEvent) {
        if (event.player.gameMode == GameMode.CREATIVE) {
            return
        }

        val block = event.block
        val result = map[block.type] ?: return

        event.isDropItems = false
        block.world.dropItem(block.location, ItemStack(result))
    }

    override fun createSupportedItems(event: RegistryFreezeEvent<*, *>): RegistryKeySet<ItemType> {
        return event.getOrCreateTag(ItemTypeTagKeys.PICKAXES)
    }
}