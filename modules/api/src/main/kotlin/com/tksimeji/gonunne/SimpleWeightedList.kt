package com.tksimeji.gonunne

import kotlin.random.Random

open class SimpleWeightedList<T>: WeightedList<T> {
    override val size: Int
        get() = map.size

    override val totalWeight: Int
        get() = map.values.sumOf { it }

    private val map = mutableMapOf<T, Int>()

    override fun add(value: T): SimpleWeightedList<T> {
        if (value is Weighted) {
            return add(value, value.weight)
        }
        return add(value, 1)
    }

    override fun add(value: T, weight: Int): SimpleWeightedList<T> {
        if (weight < 0) {
            throw IllegalArgumentException("Weight cannot be negative.")
        }
        map[value] = weight
        return this
    }

    override fun random(): T {
        if (isEmpty()) {
            throw UnsupportedOperationException("Cannot select from an entity list.")
        }
        val random = Random.nextInt(totalWeight)
        var cumulativeWeight = 0
        for ((element, weight) in map) {
            cumulativeWeight += weight
            if (random <= cumulativeWeight) {
                return element
            }
        }
        return last()
    }

    override fun contains(element: T): Boolean {
        return map.containsKey(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return map.keys.containsAll(elements)
    }

    override fun iterator(): Iterator<T> {
        return map.keys.iterator()
    }

    override fun isEmpty(): Boolean {
        return map.isEmpty()
    }

    override fun toMap(): Map<T, Int> {
        return map.toMap()
    }
}