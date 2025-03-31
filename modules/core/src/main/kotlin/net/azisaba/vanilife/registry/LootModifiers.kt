package net.azisaba.vanilife.registry

import net.azisaba.vanilife.loot.LootModifier
import java.util.*

object LootModifiers: Registry<UUID, LootModifier>() {
    fun <T: LootModifier> register(value: T): T {
        register(UUID.randomUUID(), value)
        return value
    }
}