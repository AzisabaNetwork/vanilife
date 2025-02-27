package net.azisaba.vanilife.registry

import net.azisaba.vanilife.item.*

object ItemTypes: KeyedRegistry<CustomItemType>() {
    val BILL_1000 = register(object: BillItemType(1000) {})
    val BILL_5000 = register(object: BillItemType(5000) {})
    val BILL_10000 = register(object : BillItemType(10000) {})
    val TEST = register(TestBlockItemType)
}