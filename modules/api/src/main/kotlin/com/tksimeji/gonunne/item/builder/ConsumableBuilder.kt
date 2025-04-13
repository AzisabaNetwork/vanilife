package com.tksimeji.gonunne.item.builder

import com.tksimeji.gonunne.item.Consumable
import io.papermc.paper.datacomponent.DataComponentTypes
import org.bukkit.Registry
import org.bukkit.inventory.ItemStack

internal object ConsumableBuilder: ItemStackBuilder<Consumable> {
    override val clazz: Class<Consumable> = Consumable::class.java

    override fun build(customItemType: Consumable, itemStack: ItemStack) {
        itemStack.setData(DataComponentTypes.CONSUMABLE, io.papermc.paper.datacomponent.item.Consumable.consumable()
            .consumeSeconds(customItemType.consumeSeconds)
            .animation(customItemType.consumeAnimation)
            .sound(Registry.SOUNDS.getKeyOrThrow(customItemType.consumeSound))
            .addEffects(customItemType.consumeEffects)
            .hasConsumeParticles(customItemType.hasConsumeParticle))
    }
}