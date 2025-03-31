package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemType

object CaveStoryItemType: CustomItemType {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "cave_story")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "icon/cave_story")

    override val displayName: Component
        get() = Component.text("Cave Story")

    override val rarity: ItemRarity
        get() = ItemRarity.EPIC

    override val maxStackSize: Int
        get() = 1
}