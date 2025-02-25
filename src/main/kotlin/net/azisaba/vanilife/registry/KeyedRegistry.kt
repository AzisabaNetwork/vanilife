package net.azisaba.vanilife.registry

import net.kyori.adventure.key.Key

open class KeyedRegistry<V: Keyed>: Registry<Key, V>() {
    fun <T: V> register(value: T): T {
        return register(value.key, value)
    }
}