package net.azisaba.vanilife.data

import kotlinx.serialization.Serializable

@Serializable
data class Database(
    val url: String = "jdbc:mariadb://localhost:3306/vanilife",
    val usernameEnv: String = "DATABASE_USERNAME",
    val passwordEnv: String = "DATABASE_PASSWORD",
    val maxPoolSize: Int = 10
)
