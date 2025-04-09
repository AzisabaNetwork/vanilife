package net.azisaba.vanilife.font

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.Registry
import net.kyori.adventure.key.Key

object DialogueFirstLineFont: Registry<String, Char>(), Font {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "dialogue_first_line")

    val SPACE_2 = register("space2", '\uE001')

    val SPACE_4 = register("space4", '\uE002')
}