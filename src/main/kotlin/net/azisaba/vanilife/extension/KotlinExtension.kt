package net.azisaba.vanilife.extension

import net.kyori.adventure.key.Key
import java.util.UUID

val Char.Companion.ERROR
    get() = '\uE000'

val Char.Companion.INFO
    get() = '\uE001'

fun String.toKey(): Key {
    return Key.key(this.lowercase())
}

fun String.toUuid(): UUID {
    return UUID.fromString(this)
}