package net.azisaba.vanilife.item

import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation
import net.kyori.adventure.key.Key

interface Consumable: CustomItemType {
    val consumeSeconds: Float?
        get() = null

    val consumeAnimation: ItemUseAnimation?
        get() = null

    val consumeSound: Key?
        get() = null

    val consumeEffects: List<ConsumeEffect>?
        get() = null

    val hasConsumeParticles: Boolean?
        get() = null
}