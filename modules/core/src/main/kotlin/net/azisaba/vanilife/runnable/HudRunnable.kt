package net.azisaba.vanilife.runnable

import net.azisaba.vanilife.extension.lv
import net.azisaba.vanilife.extension.money
import net.azisaba.vanilife.extension.sendHud
import net.azisaba.vanilife.font.HudFont
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import java.util.*

object HudRunnable: Runnable {
    private val cacheMap = mutableMapOf<UUID, TickCache>()

    private val levelAnimationStateMap = mutableMapOf<UUID, LevelAnimationState>()
    private val moneyAnimationStateMap = mutableMapOf<UUID, MoneyAnimationState>()

    private const val LEVEL_ANIMATION_TICKS = 28

    private const val MONEY_ANIMATION_TICKS = 6
    private const val MONEY_ANIMATION_DISPLAY_TICKS = 16

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.gameMode.isInvulnerable) {
                player.sendActionBar(Component.empty())
                continue
            }

            if (player.fishHook != null) {
                continue
            }

            val level = player.lv
            val money = player.money

            val uuid = player.uniqueId
            val cache = cacheMap[uuid] ?: TickCache(level, money)

            cacheMap[uuid] = TickCache(level, money)

            if (cache.level == level && cache.money == money && !levelAnimationStateMap.containsKey(uuid) && !moneyAnimationStateMap.containsKey(uuid)) {
                player.sendHud(level = Component.text(level.toString().padStart(2, '0')), money = Component.text(money))
                continue
            }

            val levelAnimationState = levelAnimationStateMap[uuid] ?: run {
                if (cache.level != level) LevelAnimationState(level > cache.level, LEVEL_ANIMATION_TICKS).also { levelAnimationStateMap[uuid] = it } else null
            }

            val moneyAnimationState = moneyAnimationStateMap[uuid] ?: run {
                if (cache.money != money) MoneyAnimationState(targetDifference = money - (cache.money)).also { moneyAnimationStateMap[uuid] = it } else null
            }

            var levelComponent = Component.text(level.toString().padStart(2, '0'))
            var levelIcon = HudFont.levelIcon(level)

            if (levelAnimationState != null) {
                val remainingTicks = levelAnimationState.remainingTicks

                if (((remainingTicks - 1) / 2 % 3 + 1) == 1) {
                    levelComponent = levelComponent.color(NamedTextColor.WHITE)
                    levelIcon = if (levelAnimationState.levelUp) {
                        HudFont.levelUpIcon(level, true)
                    } else {
                        HudFont.levelDownIcon(level, true)
                    }
                } else {
                    levelComponent = levelComponent.color(NamedTextColor.GRAY)
                    levelIcon = if (levelAnimationState.levelUp) {
                        HudFont.levelUpIcon(level)
                    } else {
                        HudFont.levelDownIcon(level)
                    }
                }

                levelAnimationState.remainingTicks--
            }

            var moneyComponent = Component.text(money)
            var moneyIcon = HudFont.MONEY

            if (moneyAnimationState != null) {
                val targetDifference = moneyAnimationState.targetDifference
                val displayedDifference = moneyAnimationState.displayedDifference
                val remainingTicks = moneyAnimationState.remainingTicks

                val difference = targetDifference - displayedDifference
                val step = difference / remainingTicks - MONEY_ANIMATION_DISPLAY_TICKS

                moneyAnimationState.remainingTicks--

                if (remainingTicks > MONEY_ANIMATION_DISPLAY_TICKS) {
                    moneyAnimationState.displayedDifference += step
                    moneyComponent = Component.text(String.format("%+d", moneyAnimationState.displayedDifference))
                } else {
                    moneyComponent = Component.text(String.format("%+d", moneyAnimationState.targetDifference))
                }

                moneyComponent = moneyComponent.color(if (targetDifference >= 0) NamedTextColor.GREEN else NamedTextColor.RED)

                moneyIcon = when ((remainingTicks - 1) / 2 % 3 + 1) {
                    1 -> if (targetDifference >= 0) HudFont.MONEY_IN else HudFont.MONEY_OUT
                    else -> if (targetDifference >= 0) HudFont.MONEY_IN_FLASH else HudFont.MONEY_OUT_FLASH
                }
            }

            if (levelAnimationState != null && levelAnimationState.remainingTicks <= 0) {
                levelAnimationStateMap.remove(uuid)
            }

            if (moneyAnimationState != null && moneyAnimationState.remainingTicks <= 0) {
                moneyAnimationStateMap.remove(uuid)
            }

            player.sendHud(levelComponent, levelIcon, moneyComponent, moneyIcon)
        }
    }

    private data class TickCache(
        val level: Int,
        val money: Int
    )

    private data class LevelAnimationState(
        val levelUp: Boolean,
        var remainingTicks: Int
    )

    private data class MoneyAnimationState(
        val targetDifference: Int,
        var displayedDifference: Int = 0,
        var remainingTicks: Int = MONEY_ANIMATION_TICKS + MONEY_ANIMATION_DISPLAY_TICKS,
    )
}