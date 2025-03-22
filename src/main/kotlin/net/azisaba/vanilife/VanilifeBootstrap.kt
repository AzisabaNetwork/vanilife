package net.azisaba.vanilife

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.azisaba.vanilife.command.GiveCommand

class VanilifeBootstrap: PluginBootstrap {
    override fun bootstrap(ctx: BootstrapContext) {
        ctx.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            event.registrar().register(GiveCommand.create().build())
        }
    }
}