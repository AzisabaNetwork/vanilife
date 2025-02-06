package net.azisaba.vanilife

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.event.RegistryEvents
import io.papermc.paper.registry.keys.EnchantmentKeys
import io.papermc.paper.registry.set.RegistrySet
import net.azisaba.vanilife.enchantment.Enchantments

class VanilifeBootstrap: PluginBootstrap {
    override fun bootstrap(context: BootstrapContext) {
        context.lifecycleManager.registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler { event ->
            for (enchantment in Enchantments.entries) {
                event.registry().register(EnchantmentKeys.create(enchantment.key)) { builder ->
                    builder.description(enchantment.name)
                        .maxLevel(enchantment.maxLevel)
                        .maxLevel(enchantment.maxLevel)
                        .anvilCost(enchantment.anvilCost)
                        .minimumCost(enchantment.minimumCost)
                        .maximumCost(enchantment.maximumCost)
                        .activeSlots(enchantment.activeSlots)
                        .weight(enchantment.weight)
                        .exclusiveWith(RegistrySet.keySet(RegistryKey.ENCHANTMENT, enchantment.exclusives.map { EnchantmentKeys.create(it.key()) }))
                        .supportedItems(enchantment.createSupportedItems(event))
                }
            }
        })
    }
}