package com.tksimeji.gonunne.world.biome

import com.tksimeji.gonunne.key.Keyed
import net.kyori.adventure.key.Key

enum class VanillaBiomeProvider(override val key: Key): Keyed {
    OVERWORLD(Key.key("overworld")),
    NETHER(Key.key("nether"))
}