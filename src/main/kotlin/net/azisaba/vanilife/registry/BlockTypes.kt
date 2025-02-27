package net.azisaba.vanilife.registry

import net.azisaba.vanilife.block.CustomBlockType
import net.azisaba.vanilife.block.TestBlock

object BlockTypes: KeyedRegistry<CustomBlockType>() {
    val TEST = register(TestBlock)
}