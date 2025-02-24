package net.azisaba.vanilife.vwm

import java.time.LocalDate
import java.time.Month

enum class Season(val months: Set<Month>) {
    SPRING(setOf(Month.MAY, Month.APRIL, Month.MAY)),
    SUMMER(setOf(Month.JUNE, Month.JULY, Month.AUGUST)),
    FALL(setOf(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER)),
    WINTER(setOf(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY));

    companion object {
        val now: Season
            get() = entries.first { it.months.contains(LocalDate.now().month) }
    }

    val nextSeason: Season
        get() = when (this) {
            SPRING -> SUMMER
            SUMMER -> FALL
            FALL -> WINTER
            WINTER -> SPRING
        }

    val previousSeason: Season
        get() = when (this) {
            SPRING -> WINTER
            SUMMER -> SPRING
            FALL -> SUMMER
            WINTER -> FALL
        }
}