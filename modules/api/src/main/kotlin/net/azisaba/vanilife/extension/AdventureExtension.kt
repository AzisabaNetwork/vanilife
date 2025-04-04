package net.azisaba.vanilife.extension

import net.azisaba.vanilife.font.Font
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.kyori.adventure.util.RGBLike

fun ComponentLike.font(font: Font): Component {
    return asComponent().font(font.key)
}

fun ComponentLike.normalized(): Component {
    return asComponent().colorIfAbsent(NamedTextColor.WHITE).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
}

fun ComponentLike.plainText(): String {
    return PlainTextComponentSerializer.plainText().serialize(asComponent())
}

fun RGBLike.toInt(): Int {
    require(red() in 0..255 && green() in 0..255 && blue() in 0..255)
    return (red() shl 16) or (green() shl 8) or blue()
}