package net.azisaba.vanilife.registry

import net.azisaba.vanilife.extension.toNamespacedKey
import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey

interface Keyed: net.kyori.adventure.key.Keyed, org.bukkit.Keyed {
    val key: Key

    override fun key(): Key {
        return key
    }

    override fun getKey(): NamespacedKey {
        return key.toNamespacedKey()
    }
}