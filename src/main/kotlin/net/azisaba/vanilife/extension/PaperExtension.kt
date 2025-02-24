package net.azisaba.vanilife.extension

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

fun Player.sendError(message: Component) {
    sendMessage(Component.text()
        .append(Component.text(Char.ERROR).hoverEvent(HoverEvent.showText(Component.text("エラー"))))
        .append(message.colorIfAbsent(NamedTextColor.RED)))
}

fun Player.sendInfo(message: Component) {
    sendMessage(Component.text()
        .append(Component.text(Char.INFO).hoverEvent(HoverEvent.showText(Component.text("情報"))))
        .append(message))
}