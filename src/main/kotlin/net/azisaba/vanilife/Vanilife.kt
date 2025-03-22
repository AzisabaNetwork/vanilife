package net.azisaba.vanilife

import net.azisaba.vanilife.listener.BlockListener
import net.azisaba.vanilife.listener.ExchangeListener
import net.azisaba.vanilife.runnable.HudRunnable
import net.azisaba.vanilife.util.runTaskTimerAsync
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.plugin.java.JavaPlugin

class Vanilife : JavaPlugin() {
    companion object {
        const val PLUGIN_ID = "vanilife"

        val plugin: Vanilife
            get() = _plugin

        val logger: ComponentLogger
            get() = plugin.componentLogger

        private lateinit var _plugin: Vanilife
    }

    override fun onEnable() {
        _plugin = this

        server.pluginManager.registerEvents(BlockListener, this)
        server.pluginManager.registerEvents(ExchangeListener, this)

        runTaskTimerAsync(0, 1, HudRunnable)
    }

    override fun onDisable() {
    }
}
