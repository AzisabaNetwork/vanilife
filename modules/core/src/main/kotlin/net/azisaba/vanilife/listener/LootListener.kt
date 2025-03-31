package net.azisaba.vanilife.listener

import net.azisaba.vanilife.extension.toBlockType
import net.azisaba.vanilife.loot.BlockLootModifier
import net.azisaba.vanilife.loot.ChestLootModifier
import net.azisaba.vanilife.loot.EntityLootModifier
import net.azisaba.vanilife.registry.LootModifiers
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.world.LootGenerateEvent

object LootListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        val player = event.player
        val itemStack = event.player.inventory.itemInMainHand

        val loot = block.getDrops(itemStack, player).toMutableList()

        LootModifiers.mapNotNull { it as? BlockLootModifier }
            .filter { it.targets.contains(block.type.toBlockType()) }
            .forEach { it.blockLoot(block, player, itemStack, loot) }

        if (loot.sortedBy { it.hashCode() } != block.getDrops(itemStack, player).sortedBy { it.hashCode() }) {
            event.isDropItems = false
            for (drop in loot) {
                block.world.dropItemNaturally(block.location, drop)
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity
        LootModifiers.mapNotNull { it as? EntityLootModifier }
            .filter { it.targets.contains(entity.type) }
            .forEach { it.entityLoot(entity, event.damageSource, event.drops) }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onLootGenerate(event: LootGenerateEvent) {
        val inventory = event.inventoryHolder ?: return
        LootModifiers.mapNotNull { it as? ChestLootModifier }
            .filter { it.targets.contains(event.lootTable.key) }
            .forEach { it.chestLoot(inventory, event.loot) }
    }
}