package net.azisaba.vanilife.registry

import net.kyori.adventure.key.Key

interface IRegistry<T: Keyed> {
    val entries: Set<T>

    fun get(key: Key): T?

    fun getOrThrow(key: Key): T

    fun getOrDefault(key: Key, default: T): T

    fun <E: T> register(entry: E): E
}