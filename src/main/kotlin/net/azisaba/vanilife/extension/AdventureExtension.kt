package net.azisaba.vanilife.extension

import net.azisaba.vanilife.font.Font
import net.kyori.adventure.text.Component

fun Component.font(font: Font): Component {
    return font(font.key)
}