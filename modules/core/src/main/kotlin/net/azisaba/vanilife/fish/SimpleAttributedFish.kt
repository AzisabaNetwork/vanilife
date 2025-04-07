package net.azisaba.vanilife.fish

import net.azisaba.vanilife.Range
import net.kyori.adventure.key.Key
import org.bukkit.inventory.ItemStack

class SimpleAttributedFish(
    key: Key,
    lootWeight: Int,
    item: ItemStack,
    speed: Double,
    minSpeedNoise: Double,
    maxSpeedNoise: Double,
    override val length: Range<Int>,
    override val weight: Range<Int>
): SimpleFish(key, lootWeight, item, speed, minSpeedNoise, maxSpeedNoise), AttributedFish {
}