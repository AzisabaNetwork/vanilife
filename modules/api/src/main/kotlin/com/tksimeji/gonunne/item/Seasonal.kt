package com.tksimeji.gonunne.item

import com.tksimeji.gonunne.Season

interface Seasonal: CustomItemType {
    val season: Set<Season>
}