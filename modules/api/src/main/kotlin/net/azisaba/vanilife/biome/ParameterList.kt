package net.azisaba.vanilife.biome

import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.key.Key

enum class ParameterList(override val key: Key): Keyed {
    OVERWORLD(Key.key("overworld")),
    NETHER(Key.key("nether"))
}