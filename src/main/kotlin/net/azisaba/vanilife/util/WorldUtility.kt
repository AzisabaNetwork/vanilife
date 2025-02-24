package net.azisaba.vanilife.util

import net.azisaba.vanilife.extension.toNamespacedKey
import net.kyori.adventure.key.Key
import net.kyori.adventure.util.TriState
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.World.Environment
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.ChunkGenerator

fun getWorldOrCreate(
    key: Key,
    seed: Long? = null,
    environment: Environment? = null,
    generator: ChunkGenerator? = null,
    biomeProvider: BiomeProvider? = null,
    type: WorldType? = null,
    generateStructures: Boolean? = null,
    generatorSettings: String? = null,
    hardcore: Boolean? = null,
    keepSpawnLoaded: TriState? = null
): World {
    Bukkit.getWorld(key)?.let {
        return it
    }

    val creator = WorldCreator(key.toNamespacedKey())

    seed?.let { creator.seed(seed) }
    environment?.let { creator.environment(environment) }
    generator?.let { creator.generator(generatorSettings) }
    biomeProvider?.let { creator.biomeProvider(it) }
    type?.let { creator.type(it) }
    generateStructures?.let { creator.generateStructures(it) }
    generatorSettings?.let { creator.generatorSettings(it) }
    hardcore?.let { creator.hardcore(it) }
    keepSpawnLoaded?.let { creator.keepSpawnLoaded(it) }

    return creator.createWorld() ?: throw RuntimeException("Failed to create world '${key.asString()}'.")
}