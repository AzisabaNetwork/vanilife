package net.azisaba.vanilife.font

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.Registry
import net.kyori.adventure.key.Key

object HudFont: Font, Registry<String, Char>() {
    override val key: Key
        get() = Key.key(Vanilife.PLUGIN_ID, "hud")

    val money = register(":money:", '\uE001')

    val moneyWobble1 = register(":money_wobble1:", '\uE002')

    val moneyWobble2 = register(":money_wobble2:", '\uE003')

    val space6 = register(":space6:", '\uE004')

    val space88 = register(":space88:", '\uE005')
}