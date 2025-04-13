package com.tksimeji.gonunne.enchantment.effect.impl

import com.tksimeji.gonunne.enchantment.context.EnchantmentContext
import com.tksimeji.gonunne.enchantment.effect.EnchantmentEffectEvent
import com.tksimeji.gonunne.enchantment.effect.EnchantmentEffects

@Suppress("UNCHECKED_CAST")
internal class EnchantmentEffectsImpl: EnchantmentEffects {
    internal val handlers = mutableMapOf<EnchantmentEffectEvent<*>, HandlerInfo>()

    override fun <C: EnchantmentContext> add(event: EnchantmentEffectEvent<C>, priority: Int, handler: (C) -> Unit): EnchantmentEffects {
        handlers[event] = HandlerInfo(handler as (EnchantmentContext) -> Unit, priority)
        return this
    }

    internal data class HandlerInfo(val function: (EnchantmentContext) -> Unit, val priority: Int)
}