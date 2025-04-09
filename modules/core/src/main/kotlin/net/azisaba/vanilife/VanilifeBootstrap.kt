package net.azisaba.vanilife

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.event.RegistryEvents
import io.papermc.paper.registry.keys.EnchantmentKeys
import io.papermc.paper.registry.set.RegistrySet
import net.azisaba.vanilife.command.DialogueCommand
import net.azisaba.vanilife.command.GiveCommand
import net.azisaba.vanilife.command.ItemListCommand
import net.azisaba.vanilife.registry.CustomEnchantments

class VanilifeBootstrap: PluginBootstrap {
    override fun bootstrap(ctx: BootstrapContext) {
        ctx.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            event.registrar().register(DialogueCommand.create().build())
            event.registrar().register(GiveCommand.create().build())
            event.registrar().register(ItemListCommand.create().build(), setOf("il"))
        }

        ctx.lifecycleManager.registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler { event ->
            for (customEnchantment in CustomEnchantments.values) {
                event.registry().register(EnchantmentKeys.create(customEnchantment.key)) { builder ->
                    builder.description(customEnchantment.displayName.asComponent())
                        .maxLevel(customEnchantment.maximumLevel)
                        .minimumCost(customEnchantment.minimumCost)
                        .maximumCost(customEnchantment.maximumCost)
                        .anvilCost(customEnchantment.anvilCost)
                        .activeSlots(customEnchantment.activeSlots)
                        .weight(customEnchantment.weight)
                        .exclusiveWith(RegistrySet.keySet(RegistryKey.ENCHANTMENT, customEnchantment.exclusives.toList().map { EnchantmentKeys.create(it.key()) }))
                        .supportedItems(event.getOrCreateTag(customEnchantment.supportedItems))
                }
            }
        })
    }
}