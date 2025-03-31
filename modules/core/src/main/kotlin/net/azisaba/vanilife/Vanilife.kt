package net.azisaba.vanilife

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.azisaba.vanilife.data.Config
import net.azisaba.vanilife.listener.*
import net.azisaba.vanilife.registry.CustomRecipes
import net.azisaba.vanilife.runnable.FishingHudRunnable
import net.azisaba.vanilife.runnable.HudRunnable
import net.azisaba.vanilife.util.createTableIfNotExists
import net.azisaba.vanilife.util.runTaskTimerAsync
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Vanilife : JavaPlugin() {
    companion object {
        const val PLUGIN_ID = "vanilife"

        const val DATABASE_PLAYER = "player"
        const val DATABASE_PLAYER_CHAPTER = "player_chapter"
        const val DATABASE_PLAYER_OBJECTIVE = "player_objective"

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

        createTableIfNotExists(DATABASE_PLAYER, ":uuid VARCHAR(36) NOT NULL PRIMARY KEY, score SMALLINT UNSIGNED NOT NULL")
        createTableIfNotExists(DATABASE_PLAYER_CHAPTER, ":player VARCHAR(36) NOT NULL, chapter VARCHAR(256) NOT NULL, PRIMARY KEY (player, chapter)")
        createTableIfNotExists(DATABASE_PLAYER_OBJECTIVE, ":player VARCHAR(36) NOT NULL, objective VARCHAR(256) NOT NULL, PRIMARY KEY (player, objective)")

        server.pluginManager.registerEvents(CustomEnchantmentListener, this)
        server.pluginManager.registerEvents(CustomItemListener, this)
        server.pluginManager.registerEvents(ExchangeListener, this)
        // server.pluginManager.registerEvents(FishingListener, this)
        server.pluginManager.registerEvents(LootListener, this)
        server.pluginManager.registerEvents(RecipeListener, this)

        runTaskTimerAsync(0, 1, FishingHudRunnable)
        runTaskTimerAsync(0, 1, HudRunnable)

        for (recipe in CustomRecipes) {
            Bukkit.addRecipe(recipe)
        }
    }

    override fun onDisable() {
        dataSource.close()
    }
}
