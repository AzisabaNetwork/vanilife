package net.azisaba.vanilife.font

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.Registry
import net.kyori.adventure.key.Key

object DialogueSecondLineFont: Registry<String, Char>(), Font {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "dialogue_second_line")

    val SPACE_2 = DialogueFirstLineFont.register("space2", '\uE001')

    val SPACE_4 = DialogueFirstLineFont.register("space4", '\uE002')
}