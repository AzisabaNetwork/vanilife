package net.azisaba.vanilife.registry

import net.kyori.adventure.key.Key

interface Keyed: net.kyori.adventure.key.Keyed {
    val key: Key

    override fun key(): Key {
        return key
    }
}