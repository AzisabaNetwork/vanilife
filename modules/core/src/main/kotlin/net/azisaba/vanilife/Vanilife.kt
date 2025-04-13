package net.azisaba.vanilife

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import com.github.retrooper.packetevents.PacketEvents
import com.tksimeji.gonunne.Adapter
import com.tksimeji.gonunne.key.toNamespacedKey
import com.tksimeji.gonunne.world.ParameterPoint
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import net.azisaba.vanilife.data.Config
import net.azisaba.vanilife.listener.*
import net.azisaba.vanilife.registry.CustomBiomes
import net.azisaba.vanilife.registry.CustomRecipes
import net.azisaba.vanilife.registry.LanguageBundles
import net.azisaba.vanilife.runnable.FishingHudRunnable
import net.azisaba.vanilife.runnable.HudRunnable
import net.azisaba.vanilife.util.createTableIfNotExists
import net.azisaba.vanilife.util.runTaskTimerAsync
import net.azisaba.vanilife.world.SimpleChunkGenerator
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import net.kyori.adventure.translation.GlobalTranslator
import net.kyori.adventure.translation.TranslationStore
import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Vanilife : JavaPlugin() {
    companion object {
        const val DATABASE_PLAYER = "player"

        lateinit var plugin: Vanilife

        lateinit var pluginConfig: Config

        lateinit var dataSource: HikariDataSource

        val logger: ComponentLogger
            get() = plugin.componentLogger

        val adapter: Adapter
            get() = V1_21_4

        val translationRegistry = TranslationStore.messageFormat(Key.key(PLUGIN_ID, "translation"))

        fun reloadPluginConfig() {
            pluginConfig = Yaml.default.decodeFromStream(File(plugin.dataFolder, "config.yml").inputStream())

            val hikariConfig = HikariConfig().apply {
                jdbcUrl = pluginConfig.database.url
                username = System.getenv(pluginConfig.database.usernameEnv)
                password = System.getenv(pluginConfig.database.passwordEnv)
                driverClassName = "org.mariadb.jdbc.Driver"
                maximumPoolSize = pluginConfig.database.maxPoolSize
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

    override fun onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this))
        PacketEvents.getAPI()
    }

    override fun onEnable() {
        plugin = this

        saveDefaultConfig()
        reloadPluginConfig()

        createTableIfNotExists(DATABASE_PLAYER, ":uuid VARCHAR(36) NOT NULL PRIMARY KEY, level SMALLINT UNSIGNED NOT NULL")

        server.pluginManager.registerEvents(CustomEnchantmentListener, this)
        server.pluginManager.registerEvents(CustomItemListener, this)
        server.pluginManager.registerEvents(ExchangeListener, this)
        server.pluginManager.registerEvents(FishingListener, this)
        server.pluginManager.registerEvents(LootListener, this)
        server.pluginManager.registerEvents(RecipeListener, this)
        server.pluginManager.registerEvents(SaltLakeListener, this)
        server.pluginManager.registerEvents(VillagerListener, this)

        runTaskTimerAsync(0, 1, FishingHudRunnable)
        runTaskTimerAsync(0, 1, HudRunnable)

        for (customBiome in CustomBiomes) {
            adapter.registerCustomBiome(customBiome)
        }

        for (recipe in CustomRecipes) {
            Bukkit.addRecipe(recipe)
        }

        for ((locale, bundle) in LanguageBundles.toMap()) {
            translationRegistry.registerAll(locale, bundle, true)
        }
        GlobalTranslator.translator().addSource(translationRegistry)

        PacketEvents.getAPI().init()

        val creator = WorldCreator(Key.key(PLUGIN_ID, "overworld").toNamespacedKey())
            .generator(SimpleChunkGenerator(adapter.createOverworldBiomeProvider(Pair(ParameterPoint.parameterPoint(), CustomBiomes.SALT_LAKE))))
        creator.createWorld()
    }

    override fun onDisable() {
        dataSource.close()
        PacketEvents.getAPI().terminate()
    }
}
