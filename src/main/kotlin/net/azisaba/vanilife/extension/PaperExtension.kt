package net.azisaba.vanilife.extension

import net.azisaba.vanilife.vwm.Cluster
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.entity.Player

fun Key.toNamespacedKey(): NamespacedKey {
    return NamespacedKey(namespace(), value())
}

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

val World.cluster: Cluster
    get() {
        TODO("Not yet implemented")
    }

val World.Environment.name: String
    get() = when (this) {
        World.Environment.NORMAL -> "normal"
        World.Environment.NETHER -> "the_nether"
        World.Environment.THE_END -> "the_end"
        World.Environment.CUSTOM -> "custom"
    }