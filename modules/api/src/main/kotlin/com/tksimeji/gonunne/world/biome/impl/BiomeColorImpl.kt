package com.tksimeji.gonunne.world.biome.impl

import com.tksimeji.gonunne.world.biome.BiomeColor

internal class BiomeColorImpl(private val red: Int, private val green: Int, private val blue: Int): BiomeColor {
    override fun red(): Int {
        return red
    }

    override fun green(): Int {
        return green
    }

    override fun blue(): Int {
        return blue
    }

    override fun toInt(): Int {
        require(red in 0..255 && green in 0..255 && blue in 0..255)
        return (red shl 16) or (green shl 8) or blue
    }
}