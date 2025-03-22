package net.azisaba.vanilife.loot

import kotlin.random.Random

internal class RangeImpl(override val min: Int, override val max: Int) : Range {
    override fun get(): Int {
        return Random.nextInt(min, max + 1)
    }
}