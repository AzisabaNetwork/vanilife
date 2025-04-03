package net.azisaba.vanilife.listener

import net.azisaba.vanilife.registry.CustomBiomes
import org.bukkit.Tag
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFormEvent

object SaltLakeListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockForm(event: BlockFormEvent) {
        val block = event.block
        event.isCancelled = Tag.CONCRETE_POWDER.values.contains(block.type) || block.biome == CustomBiomes.SALT_LAKE.toPaperBiome()
    }
}