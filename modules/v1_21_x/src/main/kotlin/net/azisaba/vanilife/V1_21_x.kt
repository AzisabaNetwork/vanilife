package net.azisaba.vanilife

import com.tksimeji.gonunne.Adapter
import com.tksimeji.gonunne.world.ParameterPoint
import com.tksimeji.gonunne.world.biome.CustomBiome
import net.azisaba.vanilife.extensions.*
import net.azisaba.vanilife.util.getMappedRegistry
import net.azisaba.vanilife.util.getRegistry
import net.kyori.adventure.key.Keyed
import net.minecraft.core.Holder
import net.minecraft.core.RegistrationInfo
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.*
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
import net.minecraft.world.level.levelgen.RandomState
import org.bukkit.craftbukkit.block.CraftBiome
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.WorldInfo

abstract class V1_21_x: Adapter {
    override val version: Set<String> = setOf("1.21.4")

    private val randomStates = mutableMapOf<WorldInfo, RandomState>()

    override fun registerCustomBiome(customBiome: CustomBiome) {
        val biomeRegistry = getMappedRegistry(Registries.BIOME).apply { unfrozenSilently() }
        val biome = customBiome.toMinecraftBiome()
        val holder = biomeRegistry.register(ResourceKey.create(Registries.BIOME, customBiome.key.toMinecraftResourceLocation()), biome, RegistrationInfo.BUILT_IN)

        val bindValueMethod = Holder.Reference::class.java.getDeclaredMethod("bindValue", Any::class.java).apply { isAccessible = true }
        bindValueMethod.invoke(holder, biome)

        val bindTagsMethod = Holder.Reference::class.java.getDeclaredMethod("bindTags", Collection::class.java).apply { isAccessible = true }
        bindTagsMethod.invoke(holder, emptySet<TagKey<Biome>>())

        biomeRegistry.frozenSilently()
    }

    override fun createBiomeProvider(vararg biomes: Pair<ParameterPoint, Keyed>): BiomeProvider {
        return createBiomeProvider(null, LevelStem.OVERWORLD, *biomes)
    }

    override fun createOverworldBiomeProvider(vararg biomes: Pair<ParameterPoint, Keyed>): BiomeProvider {
        return createBiomeProvider(MultiNoiseBiomeSourceParameterLists.OVERWORLD, LevelStem.OVERWORLD, *biomes)
    }

    override fun createNetherBiomeProvider(vararg biomes: Pair<ParameterPoint, Keyed>): BiomeProvider {
        return createBiomeProvider(MultiNoiseBiomeSourceParameterLists.NETHER, LevelStem.NETHER, *biomes)
    }

    private fun createBiomeProvider(parameterListKey: ResourceKey<MultiNoiseBiomeSourceParameterList>?, levelStemKey: ResourceKey<LevelStem>, vararg biomes: Pair<ParameterPoint, Keyed>): BiomeProvider {
        val parameterList = if (parameterListKey != null) getRegistry(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST).getOrThrow(parameterListKey).value().parameters().values().toMutableList() else mutableListOf()

        for ((parameterPoint, biomeKey) in biomes) {
            val biome = getRegistry(Registries.BIOME).getValueOrThrow(ResourceKey.create(Registries.BIOME, biomeKey.toMinecraftResourceLocation()))
            parameterList.add(com.mojang.datafixers.util.Pair.of(parameterPoint.toMinecraftParameterPoint(), Holder.direct(biome)))
        }

        val generatorSettings = (getRegistry(Registries.LEVEL_STEM).getValueOrThrow(levelStemKey).generator as NoiseBasedChunkGenerator).generatorSettings().value()
        val biomeSource = MultiNoiseBiomeSource.createFromList(Climate.ParameterList(parameterList))

        return object : BiomeProvider() {
            override fun getBiome(worldInfo: WorldInfo, x: Int, y: Int, z: Int): org.bukkit.block.Biome {
                val randomState = randomStates[worldInfo] ?: run {
                    RandomState.create(generatorSettings, getRegistry(Registries.NOISE), worldInfo.seed).also {
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