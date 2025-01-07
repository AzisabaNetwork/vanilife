package net.azisaba.vanilife.command

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.azisaba.vanilife.util.setMoney
import org.bukkit.entity.Player

object MoneyCommand: BasicCommand {
    override fun execute(stack: CommandSourceStack, args: Array<out String>) {
        val sender = stack.sender

        if (sender !is Player ||
            args.size != 1 ||
            args[0].toIntOrNull() == null) {
            return
        }

        sender.setMoney(args[0].toInt())
    }
}