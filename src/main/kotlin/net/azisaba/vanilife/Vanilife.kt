package net.azisaba.vanilife

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.azisaba.vanilife.data.Config
import net.azisaba.vanilife.listener.BlockListener
import net.azisaba.vanilife.listener.ExchangeListener
import net.azisaba.vanilife.runnable.HudRunnable
import net.azisaba.vanilife.util.runTaskTimerAsync
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Vanilife : JavaPlugin() {
    companion object {
        const val PLUGIN_ID = "vanilife"

        lateinit var plugin: Vanilife

        lateinit var pluginConfig: Config

        lateinit var dataSource: HikariDataSource

        val logger: ComponentLogger
            get() = plugin.componentLogger

        fun reloadPluginConfig() {
            pluginConfig = Yaml.default.decodeFromStream(File(plugin.dataFolder, "config.yml").inputStream())

            val hikariConfig = HikariConfig().apply {
                jdbcUrl = pluginConfig.database.url
                username = System.getenv(pluginConfig.database.usernameEnv)
                password = System.getenv(pluginConfig.database.passwordEnv)
                driverClassName = "org.mariadb.jdbc.Driver"
                maximumPoolSize = pluginConfig.database.maximumPoolSize
            }

            if (!::dataSource.isInitialized ||
                dataSource.isClosed ||
                dataSource.jdbcUrl != hikariConfig.jdbcUrl ||
                dataSource.username != hikariConfig.username ||
                dataSource.password != hikariConfig.password ||
                dataSource.maximumPoolSize != hikariConfig.maximumPoolSize) {
                if (::dataSource.isInitialized) {
                    dataSource.close()
                }

                dataSource = HikariDataSource(hikariConfig)
            }
        }
    }

    override fun onEnable() {
        plugin = this

        saveDefaultConfig()
        reloadPluginConfig()

        server.pluginManager.registerEvents(BlockListener, this)
        server.pluginManager.registerEvents(ExchangeListener, this)

        runTaskTimerAsync(0, 1, HudRunnable)
    }

    override fun onDisable() {
        dataSource.close()
    }
}
