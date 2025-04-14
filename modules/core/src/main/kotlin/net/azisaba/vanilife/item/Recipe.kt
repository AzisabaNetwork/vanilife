package net.azisaba.vanilife.item

import com.tksimeji.gonunne.item.CustomItemType
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

abstract class Recipe(private val recipeTitle: Component): CustomItemType, IRecipe {
    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "recipe")

    override val displayName: Component = Component.translatable("item.vanilife.recipe", recipeTitle)

    override val maxStackSize: Int = 1

    override fun use(itemStack: ItemStack, player: Player, action: Action, block: Block?, face: BlockFace) {
        learn(player)
    }
}

private interface IRecipe: CustomItemType {
    fun learn(player: Player)
}