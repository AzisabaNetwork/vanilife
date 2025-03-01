package net.azisaba.vanilife.registry

import net.azisaba.vanilife.block.CustomBlockType
import net.azisaba.vanilife.block.PortalBlockType
import net.azisaba.vanilife.block.TestBlockType

object BlockTypes: KeyedRegistry<CustomBlockType>() {
    val PORTAL = register(PortalBlockType)
    val TEST = register(TestBlockType)
}