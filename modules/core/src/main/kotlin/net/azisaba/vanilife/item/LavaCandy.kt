package net.azisaba.vanilife.item

import com.tksimeji.gonunne.item.CustomItemType
import net.azisaba.vanilife.PLUGIN_ID
import net.azisaba.vanilife.extensions.customItemType
import net.azisaba.vanilife.extensions.focusHotbarSlot
import net.azisaba.vanilife.extensions.focusItemStack
import net.azisaba.vanilife.extensions.showCharacterDialogue
import net.azisaba.vanilife.font.TitleFont
import net.azisaba.vanilife.registry.CustomItemTypes
import net.azisaba.vanilife.registry.CustomRecipes
import net.azisaba.vanilife.util.runTask
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

object LavaCandy: CustomItemType {
    override val key: Key = Key.key(PLUGIN_ID, "lava_candy")

    override val itemType: ItemType = ItemType.STICK

    override val itemModel: Key = Key.key(PLUGIN_ID, "lava_candy")

    override val displayName: Component = Component.translatable("item.vanilife.lava_candy")

    override val maxStackSize: Int = 1

    override fun onPickup(itemStack: ItemStack, player: Player) {
        player.showCharacterDialogue(Component.translatable("character.piggle.lavaCandy.1"), TitleFont.DIALOGUE_PIGGLE) {
            player.showCharacterDialogue(Component.translatable("character.piggle.lavaCandy.2"), Component.translatable("character.piggle.lavaCandy.3"), TitleFont.DIALOGUE_PIGGLE) {
                player.focusItemStack(condition = { itemStack -> itemStack.customItemType() == CustomItemTypes.LAVA_CANDY }) { index ->
                    player.focusHotbarSlot(index) {
                        player.inventory.clear(index)
                        player.showCharacterDialogue(Component.translatable("character.piggle.lavaCandy.4"), TitleFont.DIALOGUE_PIGGLE) {
                            player.showCharacterDialogue(Component.translatable("character.piggle.lavaCandy.5"), TitleFont.DIALOGUE_PIGGLE) {
                                runTask {
                                    player.discoverRecipe(CustomRecipes.MAGNITE_ENCHANTED_BOOK.key)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}