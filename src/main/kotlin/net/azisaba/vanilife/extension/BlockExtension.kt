package net.azisaba.vanilife.extension

import org.bukkit.Material
import org.bukkit.block.BlockType

fun BlockType.toMaterial(): Material {
    return Material.matchMaterial(key.asString())!!
}