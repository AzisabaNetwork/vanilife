package net.azisaba.vanilife.data

import kotlinx.serialization.Serializable

@Serializable
data class RandomTeleport(
    val range: Range = Range(-1024, 1024),
    val maxAttempts: Int = 86
)
