package net.azisaba.vanilife.registry

import io.papermc.paper.registry.RegistryKey
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.tag.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.block.BlockType

object CustomTags: KeyedRegistry<CustomTag<*>>() {
    val ORE = register(Key.key(Vanilife.PLUGIN_ID, "ore"), RegistryKey.BLOCK, setOf(BlockType.COAL_ORE,
            BlockType.DEEPSLATE_COAL_ORE,
            BlockType.IRON_ORE,
            BlockType.DEEPSLATE_IRON_ORE,
            BlockType.COPPER_ORE,
            BlockType.DEEPSLATE_COPPER_ORE,
            BlockType.GOLD_ORE,
            BlockType.DEEPSLATE_GOLD_ORE,
            BlockType.REDSTONE_ORE,
            BlockType.DEEPSLATE_REDSTONE_ORE,
            BlockType.EMERALD_ORE,
            BlockType.DEEPSLATE_EMERALD_ORE,
            BlockType.LAPIS_ORE,
            BlockType.DEEPSLATE_LAPIS_ORE,
            BlockType.DIAMOND_ORE,
            BlockType.DEEPSLATE_DIAMOND_ORE,
            BlockType.NETHER_GOLD_ORE,
            BlockType.NETHER_QUARTZ_ORE)
    )

    fun <T : Keyed> register(key: Key, registryKey: RegistryKey<T>, entries: Set<T>): CustomTag<T> {
        return CustomTagImpl(key, registryKey, entries)
    }
}