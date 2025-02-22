package net.azisaba.vanilife

import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.plugin.java.JavaPlugin

class Vanilife : JavaPlugin() {
    companion object {
        lateinit var plugin: Vanilife

        val pluginLogger: ComponentLogger
            get() = plugin.componentLogger
    }

    override fun onEnable() {
        plugin = this
    }
}