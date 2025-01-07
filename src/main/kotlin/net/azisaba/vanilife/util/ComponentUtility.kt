package net.azisaba.vanilife.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

fun Component.serialize(): String {
    return LegacyComponentSerializer.legacySection().serialize(this)
}

fun Component.plainText(): String {
    return PlainTextComponentSerializer.plainText().serialize(this)
}