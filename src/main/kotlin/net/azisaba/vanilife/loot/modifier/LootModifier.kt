package net.azisaba.vanilife.loot.modifier

import net.azisaba.vanilife.loot.Range
import net.kyori.adventure.key.Keyed
import org.bukkit.inventory.ItemStack

interface LootModifier {
    companion object {
        val instances: Set<LootModifier>
            get() = _instances.toSet()

        private val _instances = mutableSetOf<LootModifier>()

        fun register(modifier: LootModifier) {
            _instances.add(modifier)
        }

        fun randomized(type: RandomizedLootModifier.Type, target: Keyed, itemStack: ItemStack, probability: Double, amount: Range = Range.of(1), rolls: Range = Range.of(1)): RandomizedLootModifier {
            return RandomizedLootModifierImpl(type, target, itemStack, probability, amount ,rolls)
        }
    }

    val target: Keyed

    fun modify(loot: MutableList<ItemStack>)
}