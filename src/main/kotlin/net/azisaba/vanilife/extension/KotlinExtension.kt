package net.azisaba.vanilife.extension

import net.kyori.adventure.key.Key
import java.util.UUID

fun String.toKey(): Key {
    return Key.key(this.lowercase())
}

fun String.toUuid(): UUID {
    return UUID.fromString(this)
}