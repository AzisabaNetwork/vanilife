package net.azisaba.vanilife.registry

import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed

abstract class KeyedRegistry<V: Keyed>: Registry<Key, V>(), IKeyedRegistry<V> {
    override fun <T : V> register(value: T): T {
        return register(value.key(), value)
    }
}