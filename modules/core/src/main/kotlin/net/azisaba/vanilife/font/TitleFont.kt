package net.azisaba.vanilife.font

import com.tksimeji.gonunne.font.Font
import com.tksimeji.gonunne.registry.impl.RegistryImpl
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key

object TitleFont: RegistryImpl<String, Char>(), Font {
    override val key: Key = Key.key(PLUGIN_ID, "title")

    val DIALOGUE = register("dialogue", '\uE001')

    val FOCUSED_SLOT = register("focused_slot", '\uE002')

    val SPACE_HOTBAR = register("space_hotbar", '\uE003')
}