package com.tksimeji.gonunne

interface WeightedList<T>: Collection<T> {
    val totalWeight: Int

    fun add(value: T): WeightedList<T>

    fun add(value: T, weight: Int): WeightedList<T>

    fun random(): T

    fun toMap(): Map<T, Int>
}