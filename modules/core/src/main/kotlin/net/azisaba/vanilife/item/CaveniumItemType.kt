package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemType

object CaveniumItemType: CustomItemType {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "cavenium")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "cavenium")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.cavenium")

    override val rarity: ItemRarity
        get() = ItemRarity.UNCOMMON
}