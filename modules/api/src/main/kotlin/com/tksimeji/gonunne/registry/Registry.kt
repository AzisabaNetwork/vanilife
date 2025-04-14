package com.tksimeji.gonunne.registry

interface Registry<K, V>: Collection<V> {
    val values: List<V>

    fun get(key: K): V?

    fun getOrThrow(key: K): V

    fun getOrDefault(key: K, default: V): V

    fun <T: V> register(key: K, value: T): T

    fun unregister(key: K): V

    fun toMap(): Map<K, V>
}