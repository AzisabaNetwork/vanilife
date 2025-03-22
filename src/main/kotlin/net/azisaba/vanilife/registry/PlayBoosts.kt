package net.azisaba.vanilife.registry

import net.azisaba.vanilife.playboost.CaveniumPlayBoost
import net.azisaba.vanilife.playboost.PlayBoost

object PlayBoosts: KeyedRegistry<PlayBoost>() {
    val CAVENIUM = register(CaveniumPlayBoost)
}