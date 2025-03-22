package net.azisaba.vanilife.data

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val database: Database
)
