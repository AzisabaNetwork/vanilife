package net.azisaba.vanilife.item

import net.azisaba.vanilife.Season

interface Seasoned: CustomItemType {
    val season: Set<Season>
}