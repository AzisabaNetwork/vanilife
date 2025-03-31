package net.azisaba.vanilife.tag

import io.papermc.paper.registry.RegistryKey
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed

class CustomTagImpl<T: Keyed>(
    override val key: Key,
    override val registryKey: RegistryKey<T>,
    override val entries: Set<T>
): CustomTag<T> {
    override fun iterator(): Iterator<T> {
        return entries.iterator()
    }
}