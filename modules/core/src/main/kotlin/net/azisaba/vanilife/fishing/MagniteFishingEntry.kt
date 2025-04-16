package net.azisaba.vanilife.fishing

import com.tksimeji.gonunne.fishing.ItemFishingEntry
import com.tksimeji.gonunne.item.createItemStack
import net.azisaba.vanilife.PLUGIN_ID
import net.azisaba.vanilife.registry.CustomItemTypes
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.bukkit.entity.Player

object MagniteFishingEntry: ItemFishingEntry(CustomItemTypes.MAGNITE.createItemStack()) {
    override val key: Key = Key.key(PLUGIN_ID, "magnite")

    override val weight: Int = 1

    override fun condition(player: Player, location: Location): Boolean {
        return location.world.key() == Key.key("the_nether")
    }
}