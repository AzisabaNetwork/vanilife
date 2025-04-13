package com.tksimeji.gonunne.loot.context.impl

import com.tksimeji.gonunne.loot.context.EntityLootModifierContext
import org.bukkit.damage.DamageSource
import org.bukkit.entity.Entity

internal class EntityLootModifierContextImpl(override val entity: Entity, override val damageSource: DamageSource) : EntityLootModifierContext {
}