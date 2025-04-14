package net.azisaba.vanilife.loot

import com.tksimeji.gonunne.item.createItemStack
import com.tksimeji.gonunne.loot.EntityLootModifier
import com.tksimeji.gonunne.loot.context.EntityLootModifierContext
import net.azisaba.vanilife.registry.CustomItemTypes
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object RefinedMagmaCreamLootModifier: EntityLootModifier {
    override val targets: Set<EntityType> = setOf(EntityType.MAGMA_CUBE)

    override fun loot(context: EntityLootModifierContext, loot: MutableList<ItemStack>) {
        if (loot.none { it.type == Material.MAGMA_CREAM } || Random.nextFloat() > 0.07) {
            return
        }

        loot.clear()
        loot.add(CustomItemTypes.REFINED_MAGMA_CREAM.createItemStack())
    }
}