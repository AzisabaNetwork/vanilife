package net.azisaba.vanilife.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.azisaba.vanilife.vwm.Cluster
import net.azisaba.vanilife.vwm.Season
import net.kyori.adventure.text.Component
import java.time.Year

object VwmCommand: CommandCreator {
    override fun create(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("vwm")
            .then(Commands.literal("create")
                .then(Commands.argument("year", IntegerArgumentType.integer(1970))
                    .then(Commands.argument("season", SeasonArgumentType)
                        .executes(this::createCommand))))
            .then(Commands.literal("delete")
                .then(Commands.argument("cluster", ClusterArgumentType)
                    .executes(this::deleteCommand)))
    }

    private fun createCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val sender = ctx.source.sender

        val year = Year.of(ctx.getArgument("year", Int::class.java))
        val season = ctx.getArgument("season", Season::class.java)

        sender.sendMessage(Component.text("Creating a cluster..."))
        val cluster = Cluster.create(year, season)
        sender.sendMessage(Component.text("Finished creating cluster '${cluster.name}'."))
        return Command.SINGLE_SUCCESS
    }

    private fun deleteCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val sender = ctx.source.sender
        val cluster = ctx.getArgument("cluster", Cluster::class.java)

        sender.sendMessage(Component.text("Deleting a cluster..."))
        cluster.delete()
        sender.sendMessage(Component.text("Finished deleting cluster '${cluster.name}'."))
        return Command.SINGLE_SUCCESS
    }
}