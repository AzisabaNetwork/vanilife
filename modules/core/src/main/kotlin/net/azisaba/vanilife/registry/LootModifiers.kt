package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.loot.LootModifier
import com.tksimeji.gonunne.registry.impl.RegistryImpl
import net.azisaba.vanilife.loot.FireproofReelRecipeLootModifier
import net.azisaba.vanilife.loot.RefinedMagmaCreamLootModifier
import net.azisaba.vanilife.loot.RockSaltLootModifier
import java.util.*

object LootModifiers: RegistryImpl<UUID, LootModifier<*, *>>() {
    val FIREPROOF_REEL_RECIPE = register(FireproofReelRecipeLootModifier)

    val REFINED_MAGMA_CREAM = register(RefinedMagmaCreamLootModifier)

    val ROCK_SALT = register(RockSaltLootModifier)

    fun <T: LootModifier<*, *>> register(value: T): T {
        register(UUID.randomUUID(), value)
        return value
    }
}