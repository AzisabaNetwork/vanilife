package net.azisaba.vanilife

import com.tksimeji.gonunne.Adapter
import com.tksimeji.gonunne.world.ParameterPoint
import com.tksimeji.gonunne.world.biome.CustomBiome
import io.papermc.paper.potion.PotionMix
import net.azisaba.vanilife.extensions.*
import net.azisaba.vanilife.util.getMappedRegistry
import net.azisaba.vanilife.util.getRegistry
import net.kyori.adventure.key.Keyed
import net.minecraft.core.Holder
import net.minecraft.core.RegistrationInfo
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.server.MinecraftServer
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionBrewing
import net.minecraft.world.item.alchemy.Potions
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

    override fun registerPotionMixes(potionMixes: Collection<PotionMix>) {
        val server = MinecraftServer.getServer()
        val builder = PotionBrewing.Builder(server.worldData.enabledFeatures())
        builder.addContainer(Items.POTION)
        builder.addContainer(Items.SPLASH_POTION)
        builder.addContainer(Items.LINGERING_POTION)
        builder.addContainerRecipe(Items.POTION, Items.GUNPOWDER, Items.SPLASH_POTION)
        builder.addContainerRecipe(Items.SPLASH_POTION, Items.DRAGON_BREATH, Items.LINGERING_POTION)
        builder.addMix(Potions.WATER, Items.GLOWSTONE_DUST, Potions.THICK)
        builder.addMix(Potions.WATER, Items.REDSTONE, Potions.MUNDANE)
        builder.addMix(Potions.WATER, Items.NETHER_WART, Potions.AWKWARD)
        builder.addStartMix(Items.BREEZE_ROD, Potions.WIND_CHARGED)
        builder.addStartMix(Items.SLIME_BLOCK, Potions.OOZING)
        builder.addStartMix(Items.STONE, Potions.INFESTED)
        builder.addStartMix(Items.COBWEB, Potions.WEAVING)
        builder.addMix(Potions.AWKWARD, Items.GOLDEN_CARROT, Potions.NIGHT_VISION)
        builder.addMix(Potions.NIGHT_VISION, Items.REDSTONE, Potions.LONG_NIGHT_VISION)
        builder.addMix(Potions.NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, Potions.INVISIBILITY)
        builder.addMix(Potions.LONG_NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, Potions.LONG_INVISIBILITY)
        builder.addMix(Potions.INVISIBILITY, Items.REDSTONE, Potions.LONG_INVISIBILITY)
        builder.addStartMix(Items.MAGMA_CREAM, Potions.FIRE_RESISTANCE)
        // builder.addMix(Potions.FIRE_RESISTANCE, Items.REDSTONE, Potions.LONG_FIRE_RESISTANCE)
        builder.addStartMix(Items.RABBIT_FOOT, Potions.LEAPING)
        builder.addMix(Potions.LEAPING, Items.REDSTONE, Potions.LONG_LEAPING)
        builder.addMix(Potions.LEAPING, Items.GLOWSTONE_DUST, Potions.STRONG_LEAPING)
        builder.addMix(Potions.LEAPING, Items.FERMENTED_SPIDER_EYE, Potions.SLOWNESS)
        builder.addMix(Potions.LONG_LEAPING, Items.FERMENTED_SPIDER_EYE, Potions.LONG_SLOWNESS)
        builder.addMix(Potions.SLOWNESS, Items.REDSTONE, Potions.LONG_SLOWNESS)
        builder.addMix(Potions.SLOWNESS, Items.GLOWSTONE_DUST, Potions.STRONG_SLOWNESS)
        builder.addMix(Potions.AWKWARD, Items.TURTLE_HELMET, Potions.TURTLE_MASTER)
        builder.addMix(Potions.TURTLE_MASTER, Items.REDSTONE, Potions.LONG_TURTLE_MASTER)
        builder.addMix(Potions.TURTLE_MASTER, Items.GLOWSTONE_DUST, Potions.STRONG_TURTLE_MASTER)
        builder.addMix(Potions.SWIFTNESS, Items.FERMENTED_SPIDER_EYE, Potions.SLOWNESS)
        builder.addMix(Potions.LONG_SWIFTNESS, Items.FERMENTED_SPIDER_EYE, Potions.LONG_SLOWNESS)
        builder.addStartMix(Items.SUGAR, Potions.SWIFTNESS)
        builder.addMix(Potions.SWIFTNESS, Items.REDSTONE, Potions.LONG_SWIFTNESS)
        builder.addMix(Potions.SWIFTNESS, Items.GLOWSTONE_DUST, Potions.STRONG_SWIFTNESS)
        builder.addMix(Potions.AWKWARD, Items.PUFFERFISH, Potions.WATER_BREATHING)
        builder.addMix(Potions.WATER_BREATHING, Items.REDSTONE, Potions.LONG_WATER_BREATHING)
        builder.addStartMix(Items.GLISTERING_MELON_SLICE, Potions.HEALING)
        builder.addMix(Potions.HEALING, Items.GLOWSTONE_DUST, Potions.STRONG_HEALING)
        builder.addMix(Potions.HEALING, Items.FERMENTED_SPIDER_EYE, Potions.HARMING)
        builder.addMix(Potions.STRONG_HEALING, Items.FERMENTED_SPIDER_EYE, Potions.STRONG_HARMING)
        builder.addMix(Potions.HARMING, Items.GLOWSTONE_DUST, Potions.STRONG_HARMING)
        builder.addMix(Potions.POISON, Items.FERMENTED_SPIDER_EYE, Potions.HARMING)
        builder.addMix(Potions.LONG_POISON, Items.FERMENTED_SPIDER_EYE, Potions.HARMING)
        builder.addMix(Potions.STRONG_POISON, Items.FERMENTED_SPIDER_EYE, Potions.STRONG_HARMING)
        builder.addStartMix(Items.SPIDER_EYE, Potions.POISON)
        builder.addMix(Potions.POISON, Items.REDSTONE, Potions.LONG_POISON)
        builder.addMix(Potions.POISON, Items.GLOWSTONE_DUST, Potions.STRONG_POISON)
        builder.addStartMix(Items.GHAST_TEAR, Potions.REGENERATION)
        builder.addMix(Potions.REGENERATION, Items.REDSTONE, Potions.LONG_REGENERATION)
        builder.addMix(Potions.REGENERATION, Items.GLOWSTONE_DUST, Potions.STRONG_REGENERATION)
        builder.addStartMix(Items.BLAZE_POWDER, Potions.STRENGTH)
        builder.addMix(Potions.STRENGTH, Items.REDSTONE, Potions.LONG_STRENGTH)
        builder.addMix(Potions.STRENGTH, Items.GLOWSTONE_DUST, Potions.STRONG_STRENGTH)
        builder.addMix(Potions.WATER, Items.FERMENTED_SPIDER_EYE, Potions.WEAKNESS)
        builder.addMix(Potions.WEAKNESS, Items.REDSTONE, Potions.LONG_WEAKNESS)
        builder.addMix(Potions.AWKWARD, Items.PHANTOM_MEMBRANE, Potions.SLOW_FALLING)
        builder.addMix(Potions.SLOW_FALLING, Items.REDSTONE, Potions.LONG_SLOW_FALLING)
        server.potionBrewing = builder.build().apply {
            for (potionMix in potionMixes) {
                addPotionMix(potionMix)
            }
        }
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