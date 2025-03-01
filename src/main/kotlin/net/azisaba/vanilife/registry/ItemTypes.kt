package net.azisaba.vanilife.registry

import net.azisaba.vanilife.item.*
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit

object ItemTypes: KeyedRegistry<CustomItemType>() {
    val BILL_1000 = register(object: BillItemType(1000) {})
    val BILL_5000 = register(object: BillItemType(5000) {})
    val BILL_10000 = register(object : BillItemType(10000) {})
    val CAVENIUM = register(CaveniumItemType)
    val COMPRESSED_CAVENIUM = register(CompressedCaveniumItemType)
    val TEST = register(TestBlockItemType)

    override fun <T : CustomItemType> register(key: Key, value: T): T {
        value.recipe?.let { Bukkit.addRecipe(it) }
        return super.register(key, value)
    }
}