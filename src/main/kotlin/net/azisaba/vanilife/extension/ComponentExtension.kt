package net.azisaba.vanilife.extension

import net.azisaba.vanilife.font.Font
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

fun ComponentLike.font(font: Font): Component {
    return asComponent().font(font.key)
}

fun ComponentLike.normalized(): Component {
    return asComponent().colorIfAbsent(NamedTextColor.WHITE).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
}