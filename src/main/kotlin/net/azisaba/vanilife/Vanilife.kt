package net.azisaba.vanilife

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
    }

    override fun onDisable() {
    }
}
