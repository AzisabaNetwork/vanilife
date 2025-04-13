package com.tksimeji.gonunne.loot

import com.tksimeji.gonunne.loot.context.ChestLootModifierContext
import net.kyori.adventure.key.Keyed

interface ChestLootModifier: LootModifier<Keyed, ChestLootModifierContext> {
}