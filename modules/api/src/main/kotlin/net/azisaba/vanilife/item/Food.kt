package net.azisaba.vanilife.item

interface Food: Consumable {
    val nutrition: Int?
        get() = null

    val saturation: Float?
        get() = null

    val canAlwaysEat: Boolean?
        get() = null
}