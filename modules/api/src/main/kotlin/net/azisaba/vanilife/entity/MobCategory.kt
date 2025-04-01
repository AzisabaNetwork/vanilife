package net.azisaba.vanilife.entity

enum class MobCategory(max: Int, friendly: Boolean, persistent: Boolean, despawnDistance: Int) {
    MONSTER(70, false, false, 128),
    CREATURE(10, true, true, 128),
    AMBIENT(15, true, false, 128),
    AXOLOTLS(5, true, false, 128),
    UNDERGROUND_WATER_CREATURE(5, true, false, 128),
    WATER_CREATURE(5, true, false, 128),
    WATER_AMBIENT(20, true, false, 64),
    MISC(-1, true, true, 128)
}