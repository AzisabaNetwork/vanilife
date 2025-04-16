package com.tksimeji.gonunne.fishing

import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.bukkit.Registry
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootContext
import org.bukkit.loot.LootTables
import org.bukkit.util.noise.SimplexNoiseGenerator

internal class VanillaFishingEntry(override val weight: Int): FishingEntry {
    override val key: Key = Key.key(PLUGIN_ID, "vanilla")

    override fun condition(player: Player, location: Location): Boolean {
        return location.world.key() == Key.key("overworld")
    }

    override fun tick(player: Player, hook: FishHook, tickNumber: Int, noiseGenerator: SimplexNoiseGenerator) {
    }

    override fun loot(player: Player, hook: FishHook): List<ItemStack> {
        val fishingRod = player.equipment.itemInMainHand
        val context = LootContext.Builder(hook.location)
            .lootedEntity(hook)
            .killer(player)
            .luck(fishingRod.getEnchantmentLevel(Enchantment.LUCK_OF_THE_SEA) + player.getAttribute(Attribute.LUCK)!!.value.toFloat())
            .build()
        return LOOT_TABLE.populateLoot(null, context).toList()
    }

    companion object {
        private val LOOT_TABLE = Registry.LOOT_TABLES.getOrThrow(LootTables.FISHING.key()).lootTable
    }
}