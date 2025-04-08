package net.azisaba.vanilife.font

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.registry.Registry
import net.kyori.adventure.key.Key

object HudFont: Registry<String, Char>(), Font {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "hud")

    val LEVEL_BLUE = register("level_blue", '\uE001')
    
    val LEVEL_UP_BLUE = register("level_up_blue", '\uE002')

    val LEVEL_UP_BLUE_FLASH = register("level_up_blue_flash", '\uE003')

    val LEVEL_DOWN_BLUE = register("level_down_blue", '\uE004')

    val LEVEL_DOWN_BLUE_FLASH = register("level_down_blue_flash", '\uE005')

    val LEVEL_YELLOW = register("level_yellow", '\uE006')

    val LEVEL_UP_YELLOW = register("level_up_yellow", '\uE007')

    val LEVEL_UP_YELLOW_FLASH = register("level_up_yellow_flash", '\uE008')

    val LEVEL_DOWN_YELLOW = register("level_down_yellow", '\uE009')

    val LEVEL_DOWN_YELLOW_FLASH = register("level_down_yellow_flash", '\uE010')

    val LEVEL_GREEN = register("level_green", '\uE011')

    val LEVEL_UP_GREEN = register("level_up_green", '\uE012')

    val LEVEL_UP_GREEN_FLASH = register("level_up_green_flash", '\uE013')
    
    val LEVEL_DOWN_GREEN = register("level_down_green", '\uE014')
    
    val LEVEL_DOWN_GREEN_FLASH = register("level_down_green_flash", '\uE015')
    
    val LEVEL_PINK = register("level_pink", '\uE016')
    
    val LEVEL_UP_PINK = register("level_up_pink", '\uE017')
    
    val LEVEL_UP_PINK_FLASH = register("level_up_pink_flash", '\uE018')
    
    val LEVEL_DOWN_PINK = register("level_down_pink", '\uE019')
    
    val LEVEL_DOWN_PINK_FLASH = register("level_down_pink_flash", '\uE020')

    val LEVEL_PURPLE = register("level_purple", '\uE021')

    val LEVEL_UP_PURPLE = register("level_up_purple", '\uE022')

    val LEVEL_UP_PURPLE_FLASH = register("level_up_purple_flash", '\uE023')

    val LEVEL_DOWN_PURPLE = register("level_down_purple", '\uE024')
    
    val LEVEL_DOWN_PURPLE_FLASH = register("level_down_purple_flash", '\uE025')

    val MONEY = register("money", '\uE026')

    val MONEY_IN = register("money_in", '\uE027')

    val MONEY_IN_FLASH = register("money_in_flash", '\uE028')
    
    val MONEY_OUT = register("money_out", '\uE029')

    val MONEY_OUT_FLASH = register("money_out_flash", '\uE030')

    val FISHING_HOOK = register("fishing_hook", '\uE031')

    val FISHING_REEL = register("fishing_reel", '\uE032')

    val SPACE6 = register("space6", '\uE033')

    val SPACE119 = register("space119", '\uE034')

    val SPACE124 = register("space124", '\uE035')

    fun levelIcon(level: Int): Char {
        return get("level_${levelColor(level)}")!!
    }

    fun levelUpIcon(level: Int, flash: Boolean = false): Char {
        return if (!flash) {
            get("level_up_${levelColor(level)}")!!
        } else {
            get("level_up_${levelColor(level)}_flash")!!
        }
    }

    fun levelDownIcon(level: Int, flash: Boolean = false): Char {
        return if (!flash) {
            get("level_down_${levelColor(level)}")!!
        } else {
            get("level_down_${levelColor(level)}_flash")!!
        }
    }

    private fun levelColor(level: Int): String {
        return when (level) {
            in 0..9 -> "blue"
            in 10..19 -> "yellow"
            in 20..29 -> "green"
            in 30..39 -> "pink"
            else -> "purple"
        }
    }
}