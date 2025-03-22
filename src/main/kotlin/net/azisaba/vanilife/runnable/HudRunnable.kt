package net.azisaba.vanilife.runnable

import net.azisaba.vanilife.extension.money
import net.azisaba.vanilife.extension.sendMoneyHud
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import java.util.UUID

object HudRunnable: Runnable {
    private val animationStateMap = mutableMapOf<UUID, AnimationState>()

    private const val ANIMATION_TICKS = 12

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.gameMode.isInvulnerable) {
                player.sendActionBar(Component.empty())
                continue
            }

            val uuid = player.uniqueId
            val money = player.money
            val state = animationStateMap[uuid]

            if (state == null || state.targetMoney != money) {
                val startMoney = state?.displayedMoney ?: money
                animationStateMap[uuid] = AnimationState(
                    displayedMoney = startMoney,
                    targetMoney = money,
                    remainingTicks = ANIMATION_TICKS
                )
            }

            val currentState = animationStateMap[uuid]!!

            val displayedMoney = currentState.displayedMoney
            val targetMoney = currentState.targetMoney
            val remainingTicks = currentState.remainingTicks

            if (remainingTicks > 0) {
                val difference = targetMoney - displayedMoney
                val step = difference / remainingTicks

                currentState.displayedMoney += step
                currentState.remainingTicks--

                if (currentState.remainingTicks == 0) {
                    currentState.displayedMoney = targetMoney
                }

                player.sendMoneyHud(currentState.displayedMoney, if (displayedMoney < targetMoney) NamedTextColor.GREEN else NamedTextColor.RED)
            } else {
                player.sendMoneyHud(money)
            }
        }
    }

    private data class AnimationState(
        var displayedMoney: Int,
        val targetMoney: Int,
        var remainingTicks: Int
    )
}