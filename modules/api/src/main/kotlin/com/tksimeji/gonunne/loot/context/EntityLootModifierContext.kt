package com.tksimeji.gonunne.loot.context

import com.tksimeji.gonunne.loot.context.impl.EntityLootModifierContextImpl
import org.bukkit.damage.DamageSource
import org.bukkit.entity.Entity

interface EntityLootModifierContext: LootModifierContext {
    val entity: Entity

    val damageSource: DamageSource

    companion object {
        @JvmStatic
        fun create(entity: Entity, damageSource: DamageSource): EntityLootModifierContext {
            return EntityLootModifierContextImpl(entity, damageSource)
        }
    }
}