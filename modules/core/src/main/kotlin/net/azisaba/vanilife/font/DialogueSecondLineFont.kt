package net.azisaba.vanilife.font

import com.tksimeji.gonunne.font.Font
import com.tksimeji.gonunne.registry.impl.RegistryImpl
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key

object DialogueSecondLineFont: RegistryImpl<String, Char>(), Font {
    override val key: Key = Key.key(PLUGIN_ID, "dialogue_second_line")

    val SPACE_2 = DialogueFirstLineFont.register("space2", '\uE001')

    val SPACE_4 = DialogueFirstLineFont.register("space4", '\uE002')
}