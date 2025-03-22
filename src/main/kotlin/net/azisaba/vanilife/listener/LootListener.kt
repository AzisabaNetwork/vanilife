package net.azisaba.vanilife.listener

import net.azisaba.vanilife.loot.modifier.LootModifier
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.world.LootGenerateEvent
import org.bukkit.loot.Lootable

object LootListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity
        if (entity !is Lootable || !entity.hasLootTable()) {
            return
        }

        val lootTable = entity.lootTable!!.key()

        for (lootModifier in LootModifier.instances.filter { it.target.key() == lootTable }) {
            lootModifier.modify(event.drops)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onLootGenerate(event: LootGenerateEvent) {
        val lootTable = event.lootTable.key()
        for (lootModifier in LootModifier.instances.filter { it.target.key() == lootTable }) {
            lootModifier.modify(event.loot)
        }
    }
}