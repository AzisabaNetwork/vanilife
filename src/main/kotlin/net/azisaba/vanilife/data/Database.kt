package net.azisaba.vanilife.data

import kotlinx.serialization.Serializable

@Serializable
data class Database(
    val host: String,
    val port: Int
)
