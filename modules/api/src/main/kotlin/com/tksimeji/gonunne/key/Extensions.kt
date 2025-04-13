package com.tksimeji.gonunne.key

import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey

fun Key.toNamespacedKey(): NamespacedKey {
    if (this is NamespacedKey) {
        return this
    }
    return NamespacedKey(namespace(), value())
}

fun String.toKey(): Key {
    return Key.key(this)
}