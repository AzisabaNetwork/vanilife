package net.azisaba.vanilife.fish

import net.azisaba.vanilife.Range

interface AttributedFish: Fish {
    val length: Range<Int>

    val weight: Range<Int>
}