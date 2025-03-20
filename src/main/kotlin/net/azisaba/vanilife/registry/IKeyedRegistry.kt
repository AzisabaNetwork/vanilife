package net.azisaba.vanilife.registry

import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed

interface IKeyedRegistry<V: Keyed>: IRegistry<Key, V> {
    fun <T: V> register(value: T): T
}