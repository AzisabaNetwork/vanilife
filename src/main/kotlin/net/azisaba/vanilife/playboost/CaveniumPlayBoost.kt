package net.azisaba.vanilife.playboost

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key

object CaveniumPlayBoost: PlayBoost {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "cavenium")
}