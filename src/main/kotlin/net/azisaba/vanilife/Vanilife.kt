package net.azisaba.vanilife

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.connection.ConnectionPoolSettings
import net.azisaba.vanilife.kaml.Config
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

class Vanilife : JavaPlugin() {
    companion object {
        const val PLUGIN_ID = "vanilife"

        lateinit var plugin: JavaPlugin

        lateinit var pluginConfig: Config

        lateinit var mongo: MongoClient
        lateinit var database: MongoDatabase

        val logger: ComponentLogger
            get() = plugin.componentLogger
    }

    override fun onEnable() {
        plugin = this

        saveDefaultConfig()

        pluginConfig = Yaml.default.decodeFromStream(getResource("config.yml")!!)

        val connectionPoolSettings = ConnectionPoolSettings.builder()
            .maxSize(pluginConfig.database.maxSize)
            .minSize(pluginConfig.database.minSize)
            .maxWaitTime(pluginConfig.database.maxWaitTime, TimeUnit.MILLISECONDS)
            .build()

        val mongoClientSettings = MongoClientSettings.builder()
            .credential(MongoCredential.createScramSha1Credential(System.getenv(pluginConfig.database.usernameEnv),
                pluginConfig.database.name,
                System.getenv(pluginConfig.database.passwordEnv).toCharArray()))
            .applyToClusterSettings { builder -> builder.hosts(listOf(ServerAddress(pluginConfig.database.host, pluginConfig.database.port))) }
            .applyToConnectionPoolSettings { it.applySettings(connectionPoolSettings) }
            .build()

        mongo = MongoClients.create(mongoClientSettings)
        database = mongo.getDatabase(pluginConfig.database.name)
    }

    override fun onDisable() {
        mongo.close()
    }
}