package net.azisaba.vanilife

import net.kyori.adventure.text.format.TextColor
import java.time.LocalDate

enum class Season(val color: TextColor) {
    SPRING(TextColor.color(242, 156, 167)),
    SUMMER(TextColor.color(0, 154, 68)),
    FALL(TextColor.color(224, 126, 16)),
    WINTER(TextColor.color(114, 134, 161));

    companion object {
        val now: Season
            get() {
                // val month = LocalDate.now().monthValue
                val month = 8
                return when (month) {
                    in 3..5 -> SPRING
                    in 6..8 -> SUMMER
                    in 9..11 -> FALL
                    else -> WINTER
                }
            }
    }

    val next: Season
        get() = entries[(ordinal + 1) % entries.size]

    val previous: Season
        get() = entries[(ordinal - 1 + entries.size) % entries.size]
}