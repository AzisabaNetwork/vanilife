package net.azisaba.vanilife

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.event.RegistryEvents
import io.papermc.paper.registry.keys.EnchantmentKeys
import io.papermc.paper.registry.set.RegistrySet
import net.azisaba.vanilife.registry.Commands
import net.azisaba.vanilife.registry.CustomEnchantments

class Bootstrap: PluginBootstrap {
    override fun bootstrap(ctx: BootstrapContext) {
        ctx.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            for (command in Commands) {
                event.registrar().register(command.create().build(), command.description, command.aliases)
            }
        }

        ctx.lifecycleManager.registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler { event ->
            for (customEnchantment in CustomEnchantments.values) {
                event.registry().register(EnchantmentKeys.create(customEnchantment.key)) { builder ->
                    builder.description(customEnchantment.displayName.asComponent())
                        .maxLevel(customEnchantment.maxLevel)
                        .minimumCost(customEnchantment.minCost)
                        .maximumCost(customEnchantment.maxCost)
                        .anvilCost(customEnchantment.anvilCost)
                        .activeSlots(customEnchantment.activeSlots)
                        .weight(customEnchantment.enchantingTableWeight)
                        .exclusiveWith(RegistrySet.keySet(RegistryKey.ENCHANTMENT, customEnchantment.exclusiveSet.filterNotNull().map { EnchantmentKeys.create(it.key()) }))
                        .supportedItems(event.getOrCreateTag(customEnchantment.supportedItems))
                }
            }
        })
    }
}