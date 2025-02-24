package net.azisaba.vanilife.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

object VanilifeCommand: CommandCreator {
    override fun create(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("vanilife")
            .then(Commands.literal("reload")
                .requires { it.sender.hasPermission("vanilife.reload") }
                .executes(this::reloadCommand))
    }

    private fun reloadCommand(ctx: CommandContext<CommandSourceStack>): Int {
        Vanilife.reloadPluginConfig()
        ctx.source.sender.sendMessage(Component.text("Reload completed.").color(NamedTextColor.GREEN))
        return Command.SINGLE_SUCCESS
    }
}