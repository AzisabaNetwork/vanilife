package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.createItemStack
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import org.jetbrains.annotations.Range

class Money private constructor(override val price: Int): Priced {
    companion object {
        val MONEY_1000 = Money(1000)
        val MONEY_5000 = Money(5000)
        val MONEY_10000 = Money(10000)

        val TYPES = linkedMapOf(Pair(1000, MONEY_1000), Pair(5000, MONEY_5000), Pair(10000, MONEY_10000))

        val EXCHANGE_MAP = run {
            val map = mutableMapOf<ItemStack, ItemStack>()

            for (type in TYPES) {
                val price = type.key
                val itemType = type.value

                for (amount in 1..10) {
                    val result = TYPES.entries
                        .filter { it != type }
                        .firstOrNull { it.key == price * amount }?.value ?: continue

                    map.putIfAbsent(itemType.createItemStack(amount), result.createItemStack())
                    map.putIfAbsent(result.createItemStack(), itemType.createItemStack(amount))
                }
            }

            map.toMap()
        }

        fun createItemStacks(amount: Int): List<ItemStack> {
            val itemStacks = mutableListOf<ItemStack>()
            var remaining = amount

            for ((price, itemType) in TYPES.toList().sortedByDescending { it.first }) {
                val stackCount = remaining / price

                if (stackCount <= 0) {
                    continue
                }

                val maxStackSize = itemType.maxStackSize
                var remainingStackCount = stackCount

                while (remainingStackCount > 0) {
                    val stack = if (remainingStackCount >= maxStackSize) maxStackSize else remainingStackCount
                    itemStacks.add(itemType.createItemStack(stack))
                    remainingStackCount -= stack
                }

                remaining %= price
            }

            return itemStacks
        }
    }

    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "money_$price")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "money_$price")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.money", Component.text(price))

    override val group: ItemGroup = ItemGroups.MONEY

    override val maxStackSize: @Range(from = 1, to = 99) Int
        get() = 99
}