package net.azisaba.vanilife.extensions

import com.tksimeji.gonunne.item.CustomItemType
import com.tksimeji.gonunne.key.toKey
import com.tksimeji.gonunne.key.toNamespacedKey
import net.azisaba.vanilife.KEY_CUSTOM_ITEM_TYPE
import net.azisaba.vanilife.registry.CustomItemTypes
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType


fun ItemStack.customItemType(): CustomItemType? {
    val key = persistentDataContainer.get(KEY_CUSTOM_ITEM_TYPE.toNamespacedKey(), PersistentDataType.STRING)?.toKey() ?: return null
    return CustomItemTypes.get(key)
}

fun ItemStack.hasCustomItemType(): Boolean {
    return customItemType() != null
}