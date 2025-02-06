package net.azisaba.vanilife.registry

import net.kyori.adventure.key.Key

abstract class Registry<T: Keyed>: IRegistry<T> {
    private val map = mutableMapOf<Key, T>()

    override val entries: Set<T>
        get() = map.values.toSet()

    override fun get(key: Key): T? {
        return map[key]
    }

    override fun getOrThrow(key: Key): T {
        return get(key) ?: throw NullPointerException()
    }

    override fun getOrDefault(key: Key, default: T): T {
        return get(key) ?: default
    }

    override fun <E : T> register(entry: E): E {
        map[entry.key] = entry
        return entry
    }
}