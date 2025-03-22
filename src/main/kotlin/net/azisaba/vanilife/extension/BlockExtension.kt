package net.azisaba.vanilife.extension

import org.bukkit.Material
import org.bukkit.Registry
import org.bukkit.block.BlockType

fun BlockType.toMaterial(): Material {
    return Material.matchMaterial(key.asString())!!
}

fun Material.toBlockType(): BlockType {
    return Registry.BLOCK.get(key)!!
}