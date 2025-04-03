package net.azisaba.vanilife.registry

import net.azisaba.vanilife.loot.LootModifier
import net.azisaba.vanilife.loot.RockSaltLootModifier
import java.util.*

object LootModifiers: Registry<UUID, LootModifier>() {
    val ROCK_SALT = register(RockSaltLootModifier)

    fun <T: LootModifier> register(value: T): T {
        register(UUID.randomUUID(), value)
        return value
    }
}