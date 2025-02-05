package net.azisaba.vanilife.kaml

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val database: Database
) {
    @Serializable
    data class Database(
        val host: String,
        val port: Int,
        val name: String = "vanilife",
        val maxSize: Int = 50,
        val minSize: Int = 10,
        val maxWaitTime: Long = 1000,
        val usernameEnv: String = "MONGO_USERNAME",
        val passwordEnv: String = "MONGO_PASSWORD"
    )
}