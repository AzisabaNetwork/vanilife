package net.azisaba.vanilife.extension

import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey

fun Key.toNamespacedKey(): NamespacedKey {
    return NamespacedKey(namespace(), value())
}

fun NamespacedKey.toKey(): Key {
    return Key.key(asString())
}