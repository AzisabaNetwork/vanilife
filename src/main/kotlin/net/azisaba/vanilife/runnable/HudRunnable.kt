package net.azisaba.vanilife.runnable

import net.azisaba.vanilife.extension.money
import net.azisaba.vanilife.extension.sendMoneyHud
import net.azisaba.vanilife.font.HudFont
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import java.util.*

object HudRunnable: Runnable {
    private val animationStateMap = mutableMapOf<UUID, AnimationState>()

    private const val ANIMATION_TICKS = 8
    private const val DISPLAY_TICKS = 6
    private const val WOBBLE_ANIMATION_TICKS = 5

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.gameMode.isInvulnerable) {
                player.sendActionBar(Component.empty())
                continue
            }

            val uuid = player.uniqueId
            val money = player.money
            val state = animationStateMap[uuid]

            if (state == null || state.money != money) {
                animationStateMap[uuid] = AnimationState(
                    money = money,
                    targetDifference = money - (state?.money ?: money)
                )
            }

            val currentState = animationStateMap[uuid]!!

            val targetDifference = currentState.targetDifference
            val displayedDifference = currentState.displayedDifference
            val remainingTicks = currentState.remainingTicks
            val wobbleAnimationTicks = currentState.wobbleAnimationTicks

            if (remainingTicks > 0) {
                val difference = targetDifference - displayedDifference
                val step = difference / remainingTicks - DISPLAY_TICKS

                currentState.remainingTicks--

                if (remainingTicks > DISPLAY_TICKS) {
                    currentState.displayedDifference += step

                    if (currentState.wobbleAnimationTicks > 0) {
                        currentState.wobbleAnimationTicks--
                    } else {
                        currentState.wobbleAnimationTicks = WOBBLE_ANIMATION_TICKS
                    }
                } else {
                    currentState.displayedDifference = targetDifference
                }

                player.sendMoneyHud(String.format("%+d", currentState.displayedDifference), if (targetDifference >= 0) TextColor.color(0, 176, 107) else TextColor.color(255, 75, 0),
                    when (wobbleAnimationTicks) {
                        2 -> HudFont.moneyWobble1
                        0 -> HudFont.moneyWobble2
                        else -> HudFont.money
                    }
                )
            } else {
                player.sendMoneyHud(money.toString())
            }
        }
    }

    private data class AnimationState(
        val money: Int,
        val targetDifference: Int,
        var displayedDifference: Int = 0,
        var remainingTicks: Int = ANIMATION_TICKS + DISPLAY_TICKS,
        var wobbleAnimationTicks: Int = WOBBLE_ANIMATION_TICKS
    )
}