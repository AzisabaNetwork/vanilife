package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season

interface Seasonal: CustomItemType {
    val season: Set<Season>
}