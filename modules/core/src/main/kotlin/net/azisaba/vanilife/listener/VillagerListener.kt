package net.azisaba.vanilife.listener

import org.bukkit.entity.AbstractVillager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.VillagerAcquireTradeEvent
import kotlin.random.Random

object VillagerListener: Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onVillagerAcquireTrade(event: VillagerAcquireTradeEvent) {
        val villager = event.entity as? AbstractVillager ?: return

        if (Random.nextDouble() > 0.2) {
            return
        }

        /* val villagerType = if (villager is Villager) {
            when (villager.villagerType) {
                Villager.Type.DESERT -> Tradable.VillagerType.DESERT
                Villager.Type.JUNGLE -> Tradable.VillagerType.JUNGLE
                Villager.Type.PLAINS -> Tradable.VillagerType.PLAINS
                Villager.Type.SAVANNA -> Tradable.VillagerType.SAVANNA
                Villager.Type.SNOW -> Tradable.VillagerType.SNOW
                Villager.Type.SWAMP -> Tradable.VillagerType.SWAMP
                Villager.Type.TAIGA -> Tradable.VillagerType.TAIGA
                else -> Tradable.VillagerType.OTHER
            }
        } else if (villager is WanderingTrader) {
            Tradable.VillagerType.WANDERING_TRADER
        } else {
            Tradable.VillagerType.OTHER
        }

        val tradables = CustomItemTypes.asSequence()
            .mapNotNull { it as? Tradable }
            .filter { it !is Seasoned || it.season.contains(Season.now) }
            .filter { it.villagerTypes.contains(villagerType) }
            .filter { villager.recipes.none { recipe -> recipe.result == it.createItemStack() } }
            .toMutableList()

        if (tradables.isEmpty()) {
            return
        }

        var merchantRecipe: MerchantRecipe? = null

        val totalWeight = tradables.sumOf { it.weight }
        val randomValue = Random.nextInt(totalWeight)
        var cumulativeWeight = 0

        for (tradable in tradables) {
            cumulativeWeight += tradable.weight
            if (randomValue < cumulativeWeight) {
                merchantRecipe = tradable.createMerchantRecipe()
                break
            }
        }

        if (merchantRecipe != null) {
            event.recipe = merchantRecipe
        } */
    }
}