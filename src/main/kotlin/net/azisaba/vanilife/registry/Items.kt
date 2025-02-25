package net.azisaba.vanilife.registry

import net.azisaba.vanilife.item.*

object Items: KeyedRegistry<Item>() {
    val BILL_1000 = register(object: BillItem(1000) {})
    val BILL_5000 = register(object: BillItem(5000) {})
    val BILL_10000 = register(object : BillItem(10000) {})
}