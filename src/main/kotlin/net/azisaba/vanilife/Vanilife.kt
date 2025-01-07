package net.azisaba.vanilife

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.azisaba.vanilife.command.MoneyCommand
import net.azisaba.vanilife.listener.PlayerListener
import net.azisaba.vanilife.listener.ServerListener
import net.kyori.adventure.text.logger.slf4j.ComponentLogger
import org.bukkit.plugin.java.JavaPlugin

class Vanilife : JavaPlugin() {
    companion object {
        private var instance: Vanilife? = null

        fun plugin(): Vanilife {
            return instance!!
        }

        fun logger(): ComponentLogger {
            return plugin().componentLogger
        }
    }

    override fun onEnable() {
        instance = this

        server.pluginManager.registerEvents(PlayerListener, this)
        server.pluginManager.registerEvents(ServerListener, this)

        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, { event ->
            event.registrar().register("money", MoneyCommand)
        })
    }
}