package net.azisaba.vanilife.loot

import com.tksimeji.gonunne.item.createItemStack
import com.tksimeji.gonunne.loot.EntityLootModifier
import com.tksimeji.gonunne.loot.context.EntityLootModifierContext
import net.azisaba.vanilife.registry.CustomItemTypes
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object FireproofReelRecipeLootModifier: EntityLootModifier {
    override val targets: Set<EntityType> = setOf(EntityType.WITHER_SKELETON)

    override fun loot(context: EntityLootModifierContext, loot: MutableList<ItemStack>) {
        if (Random.nextFloat() < 0.05) {
            loot.add(CustomItemTypes.FIREPROOF_REEL_RECIPE.createItemStack())
        }
    }
}