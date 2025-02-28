package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.ItemStack
import net.azisaba.vanilife.registry.ItemTypes
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import org.jetbrains.annotations.ApiStatus

abstract class BillItemType(final override val price: Int): CustomItemType, Priced {
    companion object {
        val types
            get() = linkedMapOf(Pair(1000, ItemTypes.BILL_1000), Pair(5000, ItemTypes.BILL_5000), Pair(10000, ItemTypes.BILL_10000))

        fun createItemStacks(amount: Int): Collection<ItemStack> {
            val itemStacks = mutableListOf<ItemStack>()

            val stackSizeMap = mutableMapOf<Int, Int>()
            var remainingAmount = amount

            for (billType in this.types.keys.sortedDescending()) {
                val stackSize = remainingAmount / billType

                if (0 < stackSize) {
                    stackSizeMap[billType] = stackSize
                    remainingAmount %= billType
                }
            }

            for ((billType, stackSize) in stackSizeMap) {
                val item = when (billType) {
                    1000 -> ItemTypes.BILL_1000
                    5000 -> ItemTypes.BILL_5000
                    10000 -> ItemTypes.BILL_10000
                    else -> throw IllegalStateException()
                }

                val maxStackSize = item.type.maxStackSize
                var remainingStackSize = stackSize

                while (0 < remainingStackSize) {
                    val itemStackSize = if (remainingStackSize >= maxStackSize) maxStackSize else remainingStackSize
                    itemStacks.add(ItemStack(item, itemStackSize))
                    remainingStackSize -= itemStackSize
                }
            }

            return itemStacks
        }

        @ApiStatus.Experimental
        fun createItemStacks(amount: Int, type: BillItemType): Collection<ItemStack> {
            val billAmount = amount / type.price
            val maxStackSize = type.type.maxStackSize

            val itemStacks = mutableListOf<ItemStack>()
            var remainingAmount = billAmount

            while (remainingAmount > 0) {
                val currentAmount = if (remainingAmount >= maxStackSize) maxStackSize else remainingAmount
                itemStacks.add(ItemStack(type, currentAmount))
                remainingAmount -= currentAmount
            }

            return itemStacks
        }
    }

    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "${price}bill")

    override val type: ItemType = ItemType.STICK

    override val title: Component = Component.translatable("item.vanilife.bill", Component.text(price))

    override val model: Key?
        get() = Key.key(Vanilife.PLUGIN_ID, "${price}bill")
}