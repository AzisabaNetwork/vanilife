package net.azisaba.vanilife.util

import net.azisaba.vanilife.Vanilife
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

fun runTask(runnable: Runnable) {
    Bukkit.getScheduler().runTask(Vanilife.plugin, runnable)
}

fun runTaskAsync(runnable: Runnable) {
    Bukkit.getScheduler().runTaskAsynchronously(Vanilife.plugin, runnable)
}

fun runTaskLater(delay: Long, runnable: Runnable) {
    Bukkit.getScheduler().runTaskLater(Vanilife.plugin, runnable, delay)
}

fun runTaskLaterAsync(delay: Long, runnable: Runnable) {
    Bukkit.getScheduler().runTaskLaterAsynchronously(Vanilife.plugin, runnable, delay)
}

fun runTaskTimer(delay: Long, period: Long, runnable: Runnable) {
    Bukkit.getScheduler().runTaskTimer(Vanilife.plugin, runnable, delay, period)
}

fun runTaskTimer(delay: Long, period: Long, function: () -> Boolean) {
    FunctionRunnable(function).runTaskTimer(Vanilife.plugin, delay, period)
}

fun runTaskTimerAsync(delay: Long, period: Long, runnable: Runnable) {
    Bukkit.getScheduler().runTaskTimerAsynchronously(Vanilife.plugin, runnable, delay, period)
}

fun runTaskTimerAsync(delay: Long, period: Long, function: () -> Boolean) {
    FunctionRunnable(function).runTaskTimerAsynchronously(Vanilife.plugin, delay, period)
}

private class FunctionRunnable(val function: () -> Boolean): BukkitRunnable() {
    override fun run() {
        if (!function()) {
            cancel()
        }
    }
}