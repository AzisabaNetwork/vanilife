package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.loot.modifier.LootModifier
import net.azisaba.vanilife.loot.modifier.RandomizedLootModifier
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemType
import org.bukkit.loot.LootTables

object BulkMiningBookItemType: CustomItemType {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "bulk_mining_book")

    override val itemType: ItemType
        get() = ItemType.BOOK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "bulk_mining_book")

    override val displayName: ComponentLike
        get() = Component.translatable("item.vanilife.bulk_mining_book")

    override val rarity: ItemRarity
        get() = ItemRarity.UNCOMMON

    override val lootModifiers: List<LootModifier>
        get() {
            return listOf(LootModifier.randomized(RandomizedLootModifier.Type.MODIFY, LootTables.ABANDONED_MINESHAFT, createItemStack(), 0.025))
        }
}