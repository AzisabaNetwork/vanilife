package net.azisaba.vanilife.extension

import org.bukkit.Location
import kotlin.math.sqrt

fun Location.distance2D(location: Location): Double {
    val deltaX = location.x - x
    val deltaZ = location.z - z
    return sqrt(deltaX * deltaX + deltaZ * deltaZ)
}