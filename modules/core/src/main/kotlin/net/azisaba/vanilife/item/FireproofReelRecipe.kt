package net.azisaba.vanilife.item

import net.azisaba.vanilife.PLUGIN_ID
import net.azisaba.vanilife.extensions.showDialogue
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

object FireproofReelRecipe: MysteriousRecipe(Component.translatable("item.vanilife.fireproof_reel_recipe.hint"), Component.translatable("item.vanilife.fireproof_reel")) {
    override val key: Key = Key.key(PLUGIN_ID, "fireproof_reel_recipe")

    override fun learn(player: Player) {
        player.showDialogue(Component.translatable("item.vanilife.fireproof_reel_recipe.use"))
        player.inventory.setItemInMainHand(null)
    }
}