package com.tksimeji.gonunne.item

interface Food: Consumable {
    val nutrition: Int
        get() = 0

    val saturation: Float
        get() = 0F

    val canAlwaysEat: Boolean
        get() = false
}