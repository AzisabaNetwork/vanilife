package com.tksimeji.gonunne.component

import com.google.gson.JsonElement
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

fun Component.jsonText(): JsonElement {
    return GsonComponentSerializer.gson().serializeToTree(this)
}

fun Component.plainText(): String {
    return PlainTextComponentSerializer.plainText().serialize(this)
}

fun Component.resetStyle(): Component {
    return colorIfAbsent(NamedTextColor.WHITE).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
}