package net.azisaba.vanilife.listener

import com.tksimeji.gonunne.block.toBlockType
import com.tksimeji.gonunne.loot.BlockLootModifier
import com.tksimeji.gonunne.loot.ChestLootModifier
import com.tksimeji.gonunne.loot.EntityLootModifier
import com.tksimeji.gonunne.loot.context.BlockLootModifierContext
import com.tksimeji.gonunne.loot.context.ChestLootModifierContext
import com.tksimeji.gonunne.loot.context.EntityLootModifierContext
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
            .forEach { it.loot(BlockLootModifierContext.create(block, player, itemStack), loot) }

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
            .forEach { it.loot(EntityLootModifierContext.create(entity, event.damageSource), event.drops) }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onLootGenerate(event: LootGenerateEvent) {
        val inventory = event.inventoryHolder ?: return
        LootModifiers.mapNotNull { it as? ChestLootModifier }
            .filter { it.targets.contains(event.lootTable.key) }
            .forEach { it.loot(ChestLootModifierContext.create(inventory), event.loot) }
    }
}