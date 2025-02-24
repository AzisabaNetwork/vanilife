package net.azisaba.vanilife

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.azisaba.vanilife.command.TrashCommand
import net.azisaba.vanilife.command.VanilifeCommand

class VanilifeBootstrap: PluginBootstrap {
    override fun bootstrap(ctx: BootstrapContext) {
        ctx.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, { commands ->
            commands.registrar().register(VanilifeCommand.create().build())
            commands.registrar().register(TrashCommand.create().build())
        })
    }
}