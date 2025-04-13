package com.tksimeji.gonunne.item

import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation
import org.bukkit.Sound

interface Consumable: CustomItemType {
    val consumeSeconds: Float
        get() = 1.6f

    val consumeAnimation: ItemUseAnimation
        get() = ItemUseAnimation.EAT

    val consumeSound: Sound
        get() = Sound.ENTITY_GENERIC_EAT

    val consumeEffects: List<ConsumeEffect>
        get() = emptyList()

    val hasConsumeParticle: Boolean
        get() = true
}