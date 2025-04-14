package net.azisaba.vanilife.item

import com.tksimeji.gonunne.item.CustomItemType
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.inventory.ItemType

object RefinedMagmaCream: CustomItemType {
    override val key: Key = Key.key(PLUGIN_ID, "refined_magma_cream")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key("magma_cream")

    override val displayName: Component = Component.translatable("item.vanilife.refined_magma_cream")

    override val enchantmentAura: Boolean = true
}