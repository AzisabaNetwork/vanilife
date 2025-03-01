package net.azisaba.vanilife.block

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import org.bukkit.block.BlockType

object PortalBlockType: CustomBlockType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "portal")

    override val type: BlockType = BlockType.END_PORTAL
}