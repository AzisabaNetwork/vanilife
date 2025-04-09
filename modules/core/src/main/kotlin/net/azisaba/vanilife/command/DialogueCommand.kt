package net.azisaba.vanilife.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import net.azisaba.vanilife.extension.showDialogue
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

object DialogueCommand: CommandCreator {
    override fun create(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("dialogue")
            .requires { it.sender.hasPermission("vanilife.commands.dialogue") }
            .then(Commands.argument("targets", ArgumentTypes.players())
                .then(Commands.argument("first line", StringArgumentType.string())
                    .executes { ctx ->
                        dialogueCommand(ctx, ctx.getArgument("first line", String::class.java), "")
                        return@executes Command.SINGLE_SUCCESS
                    }
                    .then(Commands.argument("second line", StringArgumentType.string())
                        .executes { ctx ->
                            dialogueCommand(ctx, ctx.getArgument("first line", String::class.java), ctx.getArgument("second line", String::class.java))
                            return@executes Command.SINGLE_SUCCESS
                        })))
    }

    private fun dialogueCommand(ctx: CommandContext<CommandSourceStack>, firstLine: String, secondLine: String) {
        val sender = ctx.source.sender
        val targets = ctx.getArgument("targets", PlayerSelectorArgumentResolver::class.java).resolve(ctx.source)

        if (targets.isEmpty()) {
            sender.sendMessage(Component.translatable("argument.entity.notfound.player").color(NamedTextColor.RED))
            return
        }

        for (target in targets) {
            target.showDialogue(firstLine, secondLine)
        }

        if (targets.size > 1) {
            sender.sendMessage(Component.translatable("commands.dialogue.multiple", Component.text(targets.size)))
        } else {
            sender.sendMessage(Component.translatable("commands.dialogue.single", targets.first().name()))
        }
    }
}