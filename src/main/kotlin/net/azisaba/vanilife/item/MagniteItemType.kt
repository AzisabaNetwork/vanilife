package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.loot.modifier.LootModifier
import net.azisaba.vanilife.loot.modifier.RandomizedLootModifier
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemType
import org.bukkit.loot.LootTables

object MagniteItemType: CustomItemType {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "magnite")

    override val itemType: ItemType
        get() = ItemType.STICK

    override val itemModel: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "magnite")

    override val displayName: Component
        get() = Component.translatable("item.vanilife.magnite")

    override val rarity: ItemRarity
        get() = ItemRarity.UNCOMMON

    override val lootModifiers: List<LootModifier>
        get() = listOf(LootModifier.randomized(RandomizedLootModifier.Type.ADD, LootTables.WITHER_SKELETON, createItemStack(), 0.005))

    override fun onInHand(player: Player) {
        if (player.gameMode.isInvulnerable) {
            return
        }
        player.fireTicks += 22
    }
}