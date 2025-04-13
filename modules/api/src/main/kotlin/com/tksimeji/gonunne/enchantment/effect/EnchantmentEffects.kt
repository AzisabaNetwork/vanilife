package com.tksimeji.gonunne.enchantment.effect

import com.tksimeji.gonunne.enchantment.CustomEnchantment
import com.tksimeji.gonunne.enchantment.context.EnchantmentContext
import com.tksimeji.gonunne.enchantment.effect.impl.EnchantmentEffectsImpl

interface EnchantmentEffects {
    fun <C: EnchantmentContext> add(event: EnchantmentEffectEvent<C>, priority: Int = 0, handler: (C) -> Unit): EnchantmentEffects

    companion object {
        @JvmStatic
        fun create(): EnchantmentEffects {
            return EnchantmentEffectsImpl()
        }

        @JvmStatic
        fun <C: EnchantmentContext> call(event: EnchantmentEffectEvent<C>, context: C, target: Set<CustomEnchantment>) {
            target.map { it.effects }
                .filterIsInstance<EnchantmentEffectsImpl>()
                .flatMap { effects -> effects.handlers.filter { handler -> handler.key == event }.values }
                .sortedBy { info -> info.priority }
                .forEach { info -> info.function(context) }
        }
    }
}