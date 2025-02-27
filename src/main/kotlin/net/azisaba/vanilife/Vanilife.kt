package net.azisaba.vanilife

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListenerPriority
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import net.azisaba.vanilife.data.Config
import net.azisaba.vanilife.listener.BlockListener
import net.azisaba.vanilife.listener.PacketListener
import net.azisaba.vanilife.listener.PlayerListener
import net.azisaba.vanilife.runnable.HudRunnable
import net.azisaba.vanilife.util.createTableIfNotExists
import net.azisaba.vanilife.vwm.Cluster
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Vanilife : JavaPlugin() {
    companion object {
        const val PLUGIN_ID = "vanilife"

        lateinit var plugin: Vanilife

        lateinit var pluginConfig: Config

        lateinit var dataSource: HikariDataSource

        val pluginLogger: ComponentLogger
            get() = plugin.componentLogger

        fun reloadPluginConfig() {
            pluginConfig = Yaml.default.decodeFromStream(File(plugin.dataFolder, "config.yml").inputStream())

            val hikariConfig = HikariConfig().apply {
                jdbcUrl = pluginConfig.database.url
                username = System.getenv(pluginConfig.database.usernameEnv)
                password = System.getenv(pluginConfig.database.passwordEnv)
                driverClassName = "org.mariadb.jdbc.Driver"
                maximumPoolSize = pluginConfig.database.maxPoolSize
            }

            if (! ::dataSource.isInitialized ||
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

    override fun onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this))
        PacketEvents.getAPI().load()
        PacketEvents.getAPI().eventManager.registerListener(PacketListener, PacketListenerPriority.NORMAL)
    }

    override fun onEnable() {
        plugin = this

        saveDefaultConfig()
        reloadPluginConfig()

        createTableIfNotExists("block", ":type VARCHAR(64) NOT NULL, pdc JSON, world VARCHAR(32) NOT NULL, x INT NOT NULL, y INT NOT NULL, z INT NOT NULL, CHECK(JSON_VALID(pdc))")
        createTableIfNotExists("cluster", ":uuid VARCHAR(36) PRIMARY KEY, year SMALLINT UNSIGNED NOT NULL, season VARCHAR(6) NOT NULL, overworld VARCHAR(32) PRIMARY KEY, the_nether VARCHAR(32) PRIMARY KEY, the_end VARCHAR(32) PRIMARY KEY")

        Cluster.init()
        PacketEvents.getAPI().init()

        HudRunnable.runTaskTimerAsynchronously(this, 0L, 1L)

        server.pluginManager.registerEvents(BlockListener, this)
        server.pluginManager.registerEvents(PlayerListener, this)
    }

    override fun onDisable() {
        dataSource.close()
        PacketEvents.getAPI().terminate()
    }
}