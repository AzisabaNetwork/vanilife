package net.azisaba.vanilife.loot

import net.azisaba.vanilife.extension.createItemStack
import net.azisaba.vanilife.registry.CustomBiomes
import net.azisaba.vanilife.registry.CustomItemTypes
import org.bukkit.block.Block
import org.bukkit.block.BlockType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object RockSaltLootModifier: BlockLootModifier {
    override val targets: Set<BlockType> = setOf(BlockType.WHITE_CONCRETE_POWDER)

    override fun blockLoot(block: Block, player: Player, itemStack: ItemStack, loot: MutableList<ItemStack>) {
        if (player.gameMode.isInvulnerable || block.biome != CustomBiomes.SALT_LAKE.toPaperBiome()) {
            return
        }

        loot.clear()

        if (Random.nextDouble() < 0.05) {
            loot.clear()
            loot.add(CustomItemTypes.ROCK_SALT.createItemStack())
        }
    }
}