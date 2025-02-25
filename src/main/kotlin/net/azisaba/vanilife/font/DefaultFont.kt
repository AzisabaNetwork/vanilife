package net.azisaba.vanilife.font

import net.azisaba.vanilife.registry.Registry
import net.kyori.adventure.key.Key

object DefaultFont: Font, Registry<String, Char>() {
    override val key: Key = Key.key(Key.MINECRAFT_NAMESPACE, "default")

    val ERROR = register(":error:", '\uE000')

    val INFO = register(":info:", '\uE001')
}