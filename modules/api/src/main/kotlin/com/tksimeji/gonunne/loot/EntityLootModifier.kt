package com.tksimeji.gonunne.loot

import com.tksimeji.gonunne.loot.context.EntityLootModifierContext
import org.bukkit.entity.EntityType

interface EntityLootModifier: LootModifier<EntityType, EntityLootModifierContext> {
}