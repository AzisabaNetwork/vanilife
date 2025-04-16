package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.fishing.FishingEntry
import com.tksimeji.gonunne.registry.impl.KeyedRegistryImpl
import net.azisaba.vanilife.fishing.LavaCandyFishingEntry
import net.azisaba.vanilife.fishing.MagniteFishingEntry

object FishingEntries: KeyedRegistryImpl<FishingEntry>() {
    val LAVA_CANDY = register(LavaCandyFishingEntry)

    val MAGNITE = register(MagniteFishingEntry)

    val VANILLA = register(FishingEntry.vanilla(1))
}