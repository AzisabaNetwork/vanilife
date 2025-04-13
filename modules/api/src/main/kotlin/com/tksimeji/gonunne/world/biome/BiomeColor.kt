package com.tksimeji.gonunne.world.biome

import com.tksimeji.gonunne.world.biome.impl.BiomeColorImpl
import net.kyori.adventure.util.RGBLike

interface BiomeColor: RGBLike {
    fun toInt(): Int

    companion object {
        @JvmStatic
        fun biomeColor(red: Int, green: Int, blue: Int): BiomeColor {
            return BiomeColorImpl(red, green, blue)
        }
    }
}