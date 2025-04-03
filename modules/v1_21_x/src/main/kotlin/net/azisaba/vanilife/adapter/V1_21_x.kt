package net.azisaba.vanilife.adapter

import com.mojang.datafixers.util.Pair
import net.azisaba.vanilife.extension.frozen
import net.azisaba.vanilife.extension.minecraft
import net.azisaba.vanilife.util.getMappedRegistry
import net.azisaba.vanilife.util.getRegistry
import net.azisaba.vanilife.world.biome.CustomBiome
import net.azisaba.vanilife.world.biome.ParameterList
import net.minecraft.core.Holder
import net.minecraft.core.RegistrationInfo
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Climate
import net.minecraft.world.level.biome.MultiNoiseBiomeSource
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
import net.minecraft.world.level.levelgen.RandomState
import org.bukkit.craftbukkit.block.CraftBiome
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.WorldInfo

abstract class V1_21_x: Adapter {
    private val randomStates = mutableMapOf<WorldInfo, RandomState>()

    override fun registerCustomBiome(customBiome: CustomBiome) {
        val biomeRegistry = getMappedRegistry(Registries.BIOME).apply { frozen(false) }

        val biome = customBiome.minecraft()
        val holder = biomeRegistry.register(ResourceKey.create(Registries.BIOME, customBiome.key.minecraft()), biome, RegistrationInfo.BUILT_IN)

        val bindValueMethod = Holder.Reference::class.java.getDeclaredMethod("bindValue", Any::class.java).apply { isAccessible = true }
        bindValueMethod.invoke(holder, biome)

        val bindTagsMethod = Holder.Reference::class.java.getDeclaredMethod("bindTags", Collection::class.java).apply { isAccessible = true }
        bindTagsMethod.invoke(holder, emptySet<TagKey<Biome>>())

        biomeRegistry.frozen(true)
    }

    override fun createBiomeProvider(parameterList: ParameterList, vararg customBiomes: CustomBiome): BiomeProvider {
        val parameterListRegistry = getRegistry(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST)
        val parameterListKey = ResourceKey.create(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, parameterList.key.minecraft())
        val parameterListHolder = parameterListRegistry.getOrThrow(parameterListKey)

        val parameters = parameterListHolder.value().parameters().values().toMutableList()

        for (customBiome in customBiomes) {
            val climate = customBiome.climate ?: continue

            parameters.add(Pair.of(climate.minecraft(), CraftBiome.bukkitToMinecraftHolder(customBiome.toPaperBiome())))
        }

        val generator = parameterList.minecraft().generator as NoiseBasedChunkGenerator
        val biomeSource = MultiNoiseBiomeSource.createFromList(Climate.ParameterList(parameters))

        return object : BiomeProvider() {
            override fun getBiome(worldInfo: WorldInfo, x: Int, y: Int, z: Int): org.bukkit.block.Biome {
                val randomState = randomStates[worldInfo] ?: run {
                    RandomState.create(generator.generatorSettings().value(), getRegistry(Registries.NOISE), worldInfo.seed).also {
                        randomStates[worldInfo] = it
                    }
                }
                return CraftBiome.minecraftHolderToBukkit(biomeSource.getNoiseBiome(x shr 2, y shr 2, z shr 2, randomState.sampler()))
            }

            override fun getBiomes(worldInfo: WorldInfo): MutableList<org.bukkit.block.Biome> {
                return biomeSource.possibleBiomes().map { CraftBiome.minecraftHolderToBukkit(it) }.toMutableList()
            }
        }
    }
}