package com.tksimeji.gonunne.registry

import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed

interface KeyedRegistry<V: Keyed>: Registry<Key, V> {
    fun <T: V> register(value: T): T
}