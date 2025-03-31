package net.azisaba.vanilife.loot

import org.bukkit.damage.DamageSource
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

interface EntityLootModifier: LootModifier {
    val targets: Set<EntityType>

    fun entityLoot(entity: Entity, source: DamageSource, loot: MutableList<ItemStack>)
}