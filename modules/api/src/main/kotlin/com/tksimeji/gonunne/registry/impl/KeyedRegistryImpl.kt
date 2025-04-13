package com.tksimeji.gonunne.registry.impl

import com.tksimeji.gonunne.registry.KeyedRegistry
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed

abstract class KeyedRegistryImpl<V: Keyed>: RegistryImpl<Key, V>(), KeyedRegistry<V> {
    override fun <T : V> register(value: T): T {
        return register(value.key(), value)
    }
}