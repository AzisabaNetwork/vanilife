package net.azisaba.vanilife.item

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object CaveniumItemType: CustomItemType {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "cavenium")

    override val type: ItemType = ItemType.STICK

    override val title: Component = Component.translatable("item.vanilife.cavenium")

    override val model: Key = Key.key(Vanilife.PLUGIN_ID, "cavenium")
}