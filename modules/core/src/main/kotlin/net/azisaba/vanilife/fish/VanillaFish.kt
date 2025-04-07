package net.azisaba.vanilife.fish

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import org.bukkit.Registry
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootContext
import org.bukkit.loot.LootTables
import org.bukkit.util.noise.SimplexNoiseGenerator

object VanillaFish: Fish {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "vanilla")

    override val lootWeight: Int = 1

    private val lootTable = Registry.LOOT_TABLES.getOrThrow(LootTables.FISHING.key()).lootTable

    override fun tick(player: Player, hook: FishHook, tickNumber: Int, noiseGenerator: SimplexNoiseGenerator) {
    }

    override fun loot(player: Player, hook: FishHook): List<ItemStack> {
        val fishingRod = player.equipment.itemInMainHand
        val lootContext = LootContext.Builder(hook.location)
            .lootedEntity(hook)
            .killer(player)
            .luck(fishingRod.getEnchantmentLevel(Enchantment.LUCK_OF_THE_SEA) + player.getAttribute(Attribute.LUCK)!!.value.toFloat())
            .build()
        return lootTable.populateLoot(null, lootContext).toList()
    }
}