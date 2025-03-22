package net.azisaba.vanilife.loot

interface Range {
    companion object {
        fun of(int: Int): Range {
            return of(int, int)
        }

        fun of(min: Int, max: Int): Range {
            if (min > max) {
                throw IllegalArgumentException("min cannot be greater then max.")
            }

            return RangeImpl(min, max)
        }
    }

    val min: Int
    val max: Int

    fun get(): Int
}