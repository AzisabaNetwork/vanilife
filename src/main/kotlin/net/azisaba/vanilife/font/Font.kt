package net.azisaba.vanilife.font

import net.azisaba.vanilife.registry.IRegistry
import net.kyori.adventure.key.Key

interface Font: IRegistry<String, Char> {
    val key: Key
}