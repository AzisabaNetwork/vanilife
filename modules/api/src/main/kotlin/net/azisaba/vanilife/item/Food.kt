package net.azisaba.vanilife.item

interface Food: Consumable {
    val foodNutrition: Int?
        get() = null

    val foodSaturation: Float?
        get() = null

    val foodCanAlwaysEat: Boolean?
        get() = null
}