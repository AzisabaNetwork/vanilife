package net.azisaba.vanilife.util

import net.minecraft.core.MappedRegistry
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.server.MinecraftServer

fun <T> getRegistry(key: ResourceKey<Registry<T>>): Registry<T> {
    val registryAccess = MinecraftServer.getServer().registryAccess()
    return registryAccess.lookup(key).orElseThrow()
}

fun <T> getMappedRegistry(key: ResourceKey<Registry<T>>): MappedRegistry<T> {
    return getRegistry(key) as MappedRegistry<T>
}