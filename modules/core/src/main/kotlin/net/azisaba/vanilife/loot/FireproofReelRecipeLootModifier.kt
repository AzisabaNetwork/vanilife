package net.azisaba.vanilife.loot

import com.tksimeji.gonunne.item.createItemStack
import com.tksimeji.gonunne.loot.EntityLootModifier
import com.tksimeji.gonunne.loot.context.EntityLootModifierContext
import net.azisaba.vanilife.registry.CustomItemTypes
import net.azisaba.vanilife.registry.CustomRecipes
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object FireproofReelRecipeLootModifier: EntityLootModifier {
    override val targets: Set<EntityType> = setOf(EntityType.WITHER_SKELETON)

    override fun loot(context: EntityLootModifierContext, loot: MutableList<ItemStack>) {
        val player = context.damageSource.causingEntity as? Player ?: return
        if (!player.hasDiscoveredRecipe(CustomRecipes.FIREPROOF_REEL.key)) {
            loot.add(CustomItemTypes.FIREPROOF_REEL_RECIPE.createItemStack())
        }
    }
}