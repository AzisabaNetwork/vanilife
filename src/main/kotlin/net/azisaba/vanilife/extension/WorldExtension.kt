package net.azisaba.vanilife.extension

import net.azisaba.vanilife.vwm.Cluster
import org.bukkit.World

val World.cluster: Cluster?
    get() = Cluster.all().firstOrNull { it.contains(this) }

fun World.hasCluster(): Boolean {
    return cluster != null
}