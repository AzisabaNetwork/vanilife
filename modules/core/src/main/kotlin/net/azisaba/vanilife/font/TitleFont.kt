package net.azisaba.vanilife.font

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.Registry
import net.kyori.adventure.key.Key

object TitleFont: Registry<String, Char>(), Font {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "title")

    val DIALOGUE = register("dialogue", '\uE001')

    val FOCUSED_SLOT = register("attention_slot", '\uE002')

    val SPACE_HOTBAR = register("space", '\uE003')
}