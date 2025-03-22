package net.azisaba.vanilife.listener

import net.azisaba.vanilife.extension.customItemType
import net.azisaba.vanilife.extension.getPlayBoost
import net.azisaba.vanilife.extension.setPlayBoost
import net.azisaba.vanilife.extension.toMaterial
import net.azisaba.vanilife.item.CompressedCaveniumItemType
import net.azisaba.vanilife.registry.CustomItemTypes
import net.azisaba.vanilife.registry.CustomTags
import net.azisaba.vanilife.registry.PlayBoosts
import net.azisaba.vanilife.registry.CustomRecipes
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.CraftItemEvent
import kotlin.random.Random

object CaveniumListener: Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBreakOre(event: BlockBreakEvent) {
        val block = event.block
        val player = event.player

        if (!CustomTags.ORE.map { it.toMaterial() }.contains(block.type) || player.gameMode.isInvulnerable) {
            return
        }

        val playBoost = player.getPlayBoost(PlayBoosts.CAVENIUM)
        val probability = if (0 < playBoost) playBoost * 0.2F else 0.0001F

        if (Random.nextDouble() < probability) {
            player.discoverRecipes(CompressedCaveniumItemType.craftingRecipes.map { it.key })
            player.discoverRecipe(CustomRecipes.BULK_MINING_LV1_ENCHANTED_BOOK.key)
            player.discoverRecipe(CustomRecipes.RANGE_MINING_LV1_ENCHANTED_BOOK.key)

            if (playBoost > 0) {
                player.setPlayBoost(PlayBoosts.CAVENIUM, playBoost - 1)
            }

            block.world.dropItem(block.location, CustomItemTypes.CAVENIUM.createItemStack())
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onCraftCompressedCavenium(event: CraftItemEvent) {
        if (event.inventory.result?.customItemType == CustomItemTypes.COMPRESSED_CAVENIUM) {
            val player = event.whoClicked
            player.discoverRecipe(CustomRecipes.BULK_MINING_LV2_ENCHANTED_BOOK.key)
            player.discoverRecipe(CustomRecipes.RANGE_MINING_LV2_ENCHANTED_BOOK.key)
        }
    }
}