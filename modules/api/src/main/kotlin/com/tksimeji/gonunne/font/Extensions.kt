package com.tksimeji.gonunne.font

import net.kyori.adventure.text.Component

fun Component.font(font: Font): Component {
    return font(font.key)
}