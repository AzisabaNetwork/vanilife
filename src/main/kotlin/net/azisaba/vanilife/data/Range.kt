package net.azisaba.vanilife.data

import kotlinx.serialization.Serializable

@Serializable
data class Range(
    val min: Int,
    val max: Int
)
