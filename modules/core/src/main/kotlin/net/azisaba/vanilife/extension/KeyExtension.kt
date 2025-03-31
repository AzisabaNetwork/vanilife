package net.azisaba.vanilife.extension

import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.NamespacedKey

fun Keyed.toNamespacedKey(): NamespacedKey {
    return NamespacedKey(key().namespace(), key().value())
}

fun String.toKey(): Key {
    return Key.key(this)
}