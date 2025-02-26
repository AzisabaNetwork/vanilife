package net.azisaba.vanilife

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import net.azisaba.vanilife.command.*

class VanilifeBootstrap: PluginBootstrap {
    override fun bootstrap(ctx: BootstrapContext) {
        ctx.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, { event ->
            val commands = event.registrar()

            commands.register(VanilifeCommand.create().build())
            commands.register(GiveCommand.create().build(), setOf("give"))
            commands.register(MoneyCommand.create().build())
            commands.register(RandomTeleportCommand.create().build(), setOf("rtp"))
            commands.register(TrashCommand.create().build())
            commands.register(VwmCommand.create().build())
        })
    }
}