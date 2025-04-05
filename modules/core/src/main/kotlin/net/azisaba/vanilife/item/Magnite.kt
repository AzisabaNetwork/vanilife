package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemType

object Magnite: CustomItemType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "magnite")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "magnite")

    override val displayName: Component = Component.translatable("item.vanilife.magnite")

    override val group: ItemGroup = ItemGroups.MATERIAL

    override val rarity: ItemRarity = ItemRarity.UNCOMMON

    override fun onInHand(player: Player) {
        if (player.gameMode.isInvulnerable) {
            return
        }
        player.fireTicks += 22
    }
}