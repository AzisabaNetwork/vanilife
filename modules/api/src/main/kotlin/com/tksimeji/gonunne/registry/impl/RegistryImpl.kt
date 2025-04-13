package com.tksimeji.gonunne.registry.impl

import com.tksimeji.gonunne.registry.Registry

abstract class RegistryImpl<K, V>: Registry<K, V> {
    override val values: List<V>
        get() = map.values.toList()

    private val map = mutableMapOf<K, V>()

    override fun get(key: K): V? {
        return map[key]
    }

    override fun getOrThrow(key: K): V {
        return get(key) ?: throw NullPointerException()
    }

    override fun getOrDefault(key: K, default: V): V {
        return get(key) ?: default
    }

    override fun <T : V> register(key: K, value: T): T {
        return value.also { map[key] = it }
    }

    override fun unregister(key: K): V {
        val value = get(key) ?: throw IllegalArgumentException("$key is an invalid key.")
        return value.also { map.remove(key) }
    }

    override fun iterator(): Iterator<V> {
        return values.iterator()
    }

    override fun toMap(): Map<K, V> {
        return map.toMap()
    }
}