package net.azisaba.vanilife.item

import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object FireproofReel: Reel() {
    override val key: Key = Key.key(PLUGIN_ID, "fireproof_reel")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "reel")

    override val displayName: Component = Component.translatable("item.vanilife.fireproof_reel")

    override val combineHint: Component = Component.translatable("item.minecraft.fishing_rod")
}