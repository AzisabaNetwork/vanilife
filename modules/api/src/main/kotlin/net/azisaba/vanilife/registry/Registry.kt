package net.azisaba.vanilife.registry

abstract class Registry<K, V>: IRegistry<K, V> {
    override val values: List<V>
        get() = entries.values.toList()

    override val size: Int
        get() = entries.size

    private val entries = mutableMapOf<K, V>()

    override fun get(key: K): V? {
        return entries[key]
    }

    override fun getOrThrow(key: K): V {
        return get(key) ?: throw NullPointerException()
    }

    override fun getOrDefault(key: K, default: V): V {
        return get(key) ?: default
    }

    override fun <T : V> register(key: K, value: T): T {
        return value.also {
            entries[key] = it
        }
    }

    override fun unregister(key: K): V {
        val value = get(key) ?: throw IllegalArgumentException("$key is an invalid key.")
        return value.also {
            entries.remove(key)
        }
    }

    override fun iterator(): Iterator<V> {
        return values.iterator()
    }
}