package net.azisaba.vanilife.loot

import com.tksimeji.gonunne.item.createItemStack
import com.tksimeji.gonunne.loot.BlockLootModifier
import com.tksimeji.gonunne.loot.context.BlockLootModifierContext
import com.tksimeji.gonunne.world.toPaperBiome
import net.azisaba.vanilife.registry.CustomBiomes
import net.azisaba.vanilife.registry.CustomItemTypes
import org.bukkit.block.BlockType
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object RockSaltLootModifier: BlockLootModifier {
    override val targets: Set<BlockType> = setOf(BlockType.WHITE_CONCRETE_POWDER)

    override fun loot(context: BlockLootModifierContext, loot: MutableList<ItemStack>) {
        if (context.player.gameMode.isInvulnerable || context.block.biome != CustomBiomes.SALT_LAKE.toPaperBiome()) {
            return
        }
        loot.clear()
        if (Random.nextFloat() < 0.05) {
            loot.clear()
            loot.add(CustomItemTypes.ROCK_SALT.createItemStack())
        }
    }
}