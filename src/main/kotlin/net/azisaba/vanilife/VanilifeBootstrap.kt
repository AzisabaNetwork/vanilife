package net.azisaba.vanilife

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.event.RegistryEvents
import io.papermc.paper.registry.keys.EnchantmentKeys
import io.papermc.paper.registry.set.RegistrySet
import net.azisaba.vanilife.command.*
import net.azisaba.vanilife.registry.Enchantments

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

        ctx.lifecycleManager.registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler { event ->
            for (enchantment in Enchantments.values) {
                event.registry().register(EnchantmentKeys.create(enchantment.key)) { builder ->
                    builder.description(enchantment.title)
                        .maxLevel(enchantment.maxLevel)
                        .maximumCost(enchantment.maximumCost)
                        .minimumCost(enchantment.minimumCost)
                        .anvilCost(enchantment.anvilCost)
                        .activeSlots(enchantment.activeSlots)
                        .weight(enchantment.weight)
                        .exclusiveWith(RegistrySet.keySet(RegistryKey.ENCHANTMENT, enchantment.exclusives.toList().map { EnchantmentKeys.create(it.key()) }))
                        .supportedItems(event.getOrCreateTag(enchantment.supportedItems))
                }
            }
        })
    }
}