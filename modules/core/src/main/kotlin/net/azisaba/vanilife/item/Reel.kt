package net.azisaba.vanilife.item

import com.tksimeji.gonunne.component.resetStyle
import com.tksimeji.gonunne.item.Combinable
import com.tksimeji.gonunne.key.toNamespacedKey
import net.azisaba.vanilife.KEY_REEL
import net.azisaba.vanilife.extensions.reel
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import org.bukkit.persistence.PersistentDataType

abstract class Reel: Combinable {
    override val itemType: ItemType = ItemType.STICK

    override val maxStackSize: Int = 1

    override val combineHint: Component = Component.translatable("item.minecraft.fishing_rod")

    override fun combine(player: Player, itemStack: ItemStack, other: ItemStack) {
        player.playSound(player, Sound.ITEM_BUNDLE_INSERT, 1.0f, 1.0f)
        other.itemMeta = other.itemMeta.apply {
            displayName(Component.translatable("item.minecraft.fishing_rod.reeled", this@Reel.displayName).resetStyle())
            persistentDataContainer.set(KEY_REEL.toNamespacedKey(), PersistentDataType.STRING, key.asString())
        }
    }

    override fun canCombine(other: ItemStack): Boolean {
        return other.type == Material.FISHING_ROD && other.reel() == null
    }
}