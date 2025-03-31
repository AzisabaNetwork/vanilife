package net.azisaba.vanilife.tag

import io.papermc.paper.registry.RegistryKey
import net.azisaba.vanilife.registry.Keyed

interface CustomTag<T: net.kyori.adventure.key.Keyed>: Iterable<T>, Keyed {
    val registryKey: RegistryKey<T>

    val entries: Set<T>
}