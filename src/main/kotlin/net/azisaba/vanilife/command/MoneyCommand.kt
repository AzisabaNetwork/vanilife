package net.azisaba.vanilife.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import net.azisaba.vanilife.extension.money

object MoneyCommand: CommandCreator {
    override fun create(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("money")
            .then(Commands.literal("add")
                .then(Commands.argument("targets", ArgumentTypes.players())
                    .then(Commands.argument("amount", IntegerArgumentType.integer())
                        .executes(this::addCommand))))
            .then(Commands.literal("remove")
                .then(Commands.argument("targets", ArgumentTypes.players())
                    .then(Commands.argument("amount", IntegerArgumentType.integer())
                        .executes(this::removeCommand))))
            .then(Commands.literal("set")
                .then(Commands.argument("targets", ArgumentTypes.players())
                    .then(Commands.argument("amount", IntegerArgumentType.integer())
                        .executes(this::setCommand))))
    }

    private fun addCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val targets = ctx.getArgument("targets", PlayerSelectorArgumentResolver::class.java).resolve(ctx.source)
        val amount = ctx.getArgument("amount", Int::class.java)

        for (target in targets) {
            target.money += amount
        }

        return Command.SINGLE_SUCCESS
    }

    private fun removeCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val targets = ctx.getArgument("targets", PlayerSelectorArgumentResolver::class.java).resolve(ctx.source)
        val amount = ctx.getArgument("amount", Int::class.java)

        for (target in targets) {
            target.money -= amount
        }

        return Command.SINGLE_SUCCESS
    }

    private fun setCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val targets = ctx.getArgument("targets", PlayerSelectorArgumentResolver::class.java).resolve(ctx.source)
        val amount = ctx.getArgument("amount", Int::class.java)

        for (target in targets) {
            target.money = amount
        }

        return Command.SINGLE_SUCCESS
    }
}