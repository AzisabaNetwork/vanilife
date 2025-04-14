package net.azisaba.vanilife.item

import com.tksimeji.gonunne.component.resetStyle
import com.tksimeji.gonunne.key.toNamespacedKey
import net.azisaba.vanilife.PLUGIN_ID
import net.azisaba.vanilife.extensions.showDialogue
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

abstract class MysteriousRecipe(hint: Component, private val actualRecipeTitle: Component): Recipe(OBFUSCATED_RECIPE_TITLE) {
    override val lore: List<Component> = listOf(
        Component.translatable("item.vanilife.mysterious_recipe.hint").color(NamedTextColor.WHITE)
        .append(Component.text(": ").color(NamedTextColor.GRAY))
        .append(hint.colorIfAbsent(NamedTextColor.DARK_GRAY)))

    override fun use(itemStack: ItemStack, player: Player, action: Action, block: Block?, face: BlockFace) {
        if (isLocked(itemStack)) {
            player.showDialogue(Component.translatable("item.vanilife.mysterious_recipe.failed"))
            return
        }
        learn(player)
    }

    fun lock(itemStack: ItemStack) {
        itemStack.itemMeta = itemStack.itemMeta.apply {
            displayName(Component.translatable("item.vanilife.recipe", OBFUSCATED_RECIPE_TITLE).resetStyle())
            lore(this@MysteriousRecipe.lore.map { it.resetStyle() })
            persistentDataContainer.set(KEY_LOCKED.toNamespacedKey(), PersistentDataType.BOOLEAN, true)
        }
    }

    fun unlock(itemStack: ItemStack) {
        itemStack.itemMeta = itemStack.itemMeta.apply {
            displayName(Component.translatable("item.vanilife.recipe", actualRecipeTitle).resetStyle())
            lore(emptyList())
            persistentDataContainer.set(KEY_LOCKED.toNamespacedKey(), PersistentDataType.BOOLEAN, false)
        }
    }

    fun isLocked(itemStack: ItemStack): Boolean {
        return itemStack.persistentDataContainer.getOrDefault(KEY_LOCKED.toNamespacedKey(),
            PersistentDataType.BOOLEAN, true)
    }

    companion object {
        private val KEY_LOCKED = Key.key(PLUGIN_ID, "locked")

        private val OBFUSCATED_RECIPE_TITLE = Component.text("???").decorate(TextDecoration.OBFUSCATED)
    }
}