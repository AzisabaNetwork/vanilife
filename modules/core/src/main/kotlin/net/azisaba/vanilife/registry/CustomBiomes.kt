package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.registry.impl.KeyedRegistryImpl
import com.tksimeji.gonunne.world.biome.CustomBiome
import net.azisaba.vanilife.world.biome.SaltLake

object CustomBiomes: KeyedRegistryImpl<CustomBiome>() {
    val SALT_LAKE = register(SaltLake)
}