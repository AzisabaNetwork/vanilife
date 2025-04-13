package com.tksimeji.gonunne.enchantment.effect

import com.tksimeji.gonunne.enchantment.context.BlockBreakContext
import com.tksimeji.gonunne.enchantment.context.EnchantmentContext

interface EnchantmentEffectEvent<C: EnchantmentContext> {
    companion object {
        @JvmStatic
        val BREAK_BLOCK = object: EnchantmentEffectEvent<BlockBreakContext> {}
    }
}