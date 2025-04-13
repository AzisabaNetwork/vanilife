package com.tksimeji.gonunne

import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.translation.Translatable
import java.time.LocalDate

enum class Season(val months: Set<Int>, val textColor: TextColor): Translatable {
    SPRING(setOf(3, 4, 5), TextColor.color(242, 156, 167)),
    SUMMER(setOf(6, 7, 8), TextColor.color(0, 154, 68)),
    FALL(setOf(9, 10, 11), TextColor.color(224, 126, 16)),
    WINTER(setOf(12, 1, 2), TextColor.color(114, 134, 161));

    companion object {
        fun now(): Season {
            val month = LocalDate.now().monthValue
            return entries.first { it.months.contains(month) }
        }
    }

    fun next(): Season {
        return entries[(ordinal + 1) % entries.size]
    }

    fun previous(): Season {
        return entries[(ordinal - 1 + entries.size) % entries.size]
    }

    override fun translationKey(): String {
        return "season.${name.lowercase()}"
    }
}