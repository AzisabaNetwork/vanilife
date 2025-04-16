package net.azisaba.vanilife.fishing

import com.tksimeji.gonunne.fishing.FishingEntry
import com.tksimeji.gonunne.item.createItemStack
import net.azisaba.vanilife.PLUGIN_ID
import net.azisaba.vanilife.registry.CustomItemTypes
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.noise.SimplexNoiseGenerator

object LavaCandyFishingEntry: FishingEntry {
    override val key: Key = Key.key(PLUGIN_ID, "lava_candy")

    override val weight: Int = 1

    override fun condition(player: Player, location: Location): Boolean {
        return true
    }

    override fun tick(player: Player, hook: FishHook, tickNumber: Int, noiseGenerator: SimplexNoiseGenerator) {
    }

    override fun loot(player: Player, hook: FishHook): List<ItemStack> {
        return listOf(CustomItemTypes.LAVA_CANDY.createItemStack())
    }
}