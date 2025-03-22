package net.azisaba.vanilife.loot.modifier

import net.azisaba.vanilife.loot.Range
import net.kyori.adventure.key.Keyed
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

internal class RandomizedLootModifierImpl(
    override val type: RandomizedLootModifier.Type,
    override val target: Keyed,
    override val itemStack: ItemStack,
    override val probability: Double,
    override val amount: Range,
    override val rolls: Range
) : RandomizedLootModifier {
    override fun modify(loot: MutableList<ItemStack>) {
        for (i in 1..rolls.get()) {
            if (Random.nextDouble() > probability) {
                continue
            }

            val itemStack = itemStack.clone().apply { amount = this@RandomizedLootModifierImpl.amount.get() }

            if (type == RandomizedLootModifier.Type.ADD) {
                loot.add(itemStack)
            } else if (type == RandomizedLootModifier.Type.MODIFY && loot.isNotEmpty()) {
                val index = Random.nextInt(loot.size)
                loot[index] = itemStack
            }
        }
    }
}