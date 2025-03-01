package net.azisaba.vanilife.portal

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.ItemStack
import net.azisaba.vanilife.registry.ItemTypes
import net.kyori.adventure.key.Key
import org.bukkit.block.BlockType
import org.bukkit.inventory.ItemStack

object CaveworldPortal: Portal {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "caveworld")

    override val item: ItemStack = ItemStack(ItemTypes.COMPRESSED_CAVENIUM)

    override val fluidType: BlockType = BlockType.WATER

    override val frameType: BlockType = BlockType.AMETHYST_CLUSTER
}