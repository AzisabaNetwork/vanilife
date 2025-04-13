package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.SimpleWeightedList
import com.tksimeji.gonunne.fishing.FishingEntry
import com.tksimeji.gonunne.item.createItemStack

object FishingEntries: SimpleWeightedList<FishingEntry>() {
    val TUNA = add(FishingEntry.runaway(1, 0.1, 1.0, 3.0) { _, _ ->
        return@runaway listOf(CustomItemTypes.TUNA.createItemStack())
    })

    val VANILLA = add(FishingEntry.vanilla(1))
}