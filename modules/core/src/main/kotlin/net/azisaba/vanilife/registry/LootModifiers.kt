package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.loot.LootModifier
import com.tksimeji.gonunne.registry.impl.RegistryImpl
import net.azisaba.vanilife.loot.RockSaltLootModifier
import java.util.*

object LootModifiers: RegistryImpl<UUID, LootModifier<*, *>>() {
    val ROCK_SALT = register(RockSaltLootModifier)

    fun <T: LootModifier<*, *>> register(value: T): T {
        register(UUID.randomUUID(), value)
        return value
    }
}