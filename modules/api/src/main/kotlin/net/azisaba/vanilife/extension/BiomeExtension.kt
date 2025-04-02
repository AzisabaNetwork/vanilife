package net.azisaba.vanilife.extension

import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.azisaba.vanilife.biome.CustomBiome
import org.bukkit.block.Biome

fun CustomBiome.paper(): Biome {
    return RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).get(key())!!
}