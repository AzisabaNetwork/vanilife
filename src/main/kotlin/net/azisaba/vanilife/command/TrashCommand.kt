package net.azisaba.vanilife.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.azisaba.vanilife.gui.TrashGUI
import org.bukkit.entity.Player

object TrashCommand {
    fun create(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("trash")
            .requires { it.sender is Player }
            .executes { ctx ->
                TrashGUI(ctx.source.sender as Player)
                Command.SINGLE_SUCCESS
            }
    }
}