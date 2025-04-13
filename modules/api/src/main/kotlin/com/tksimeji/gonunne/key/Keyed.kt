package com.tksimeji.gonunne.key

import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed

interface Keyed: Keyed {
    val key: Key

    override fun key(): Key {
        return key
    }
}