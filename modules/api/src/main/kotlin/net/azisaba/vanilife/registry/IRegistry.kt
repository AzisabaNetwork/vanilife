package net.azisaba.vanilife.registry

interface IRegistry<K, V>: Iterable<V> {
    val values: List<V>

    val size: Int

    fun get(key: K): V?

    fun getOrThrow(key: K): V

    fun getOrDefault(key: K, default: V): V

    fun <T: V> register(key: K, value: T): T

    fun unregister(key: K): V

    fun toMap(): Map<K, V>
}