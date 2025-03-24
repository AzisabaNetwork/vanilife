package net.azisaba.vanilife.listener

import net.azisaba.vanilife.extension.toNamespacedKey
import net.azisaba.vanilife.loot.modifier.LootModifier
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.world.LootGenerateEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.Lootable

object LootListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        val lootTable = Bukkit.getLootTable(Key.key("blocks/${block.type.key().value()}").toNamespacedKey()) ?: return

        val player = event.player
        val itemStack = event.player.inventory.itemInMainHand
        val loot = block.getDrops(itemStack, player).toMutableList()

        if (modify(lootTable, loot) > 0) {
            event.isDropItems = false
            for (aItemStack in loot) {
                block.world.dropItemNaturally(block.location, aItemStack)
            }
        }
        player.exhaustion
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity
        if (entity !is Lootable || !entity.hasLootTable()) {
            return
        }

        val lootTable = entity.lootTable!!.key()
        modify(lootTable, event.drops)
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onLootGenerate(event: LootGenerateEvent) {
        val lootTable = event.lootTable.key()
        modify(lootTable, event.loot)
    }

    private fun modify(lootTable: Keyed, loot: MutableList<ItemStack>): Int {
        val modifiers = LootModifier.instances.filter { it.target.key() == lootTable.key() }
        for (modifier in modifiers) {
            modifier.modify(loot)
        }
        return modifiers.size
    }
}