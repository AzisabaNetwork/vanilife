package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.loot.modifier.LootModifier
import net.azisaba.vanilife.loot.modifier.RandomizedLootModifier
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object RockSaltItemType: CustomItemType {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "rock_salt")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "rock_salt")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.rock_salt")

    override val lootModifiers: List<LootModifier>
        get() = listOf(LootModifier.randomized(RandomizedLootModifier.Type.ADD, Key.key("blocks/stone"), createItemStack(), 0.005))
}