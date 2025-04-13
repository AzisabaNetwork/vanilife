package net.azisaba.vanilife.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.tksimeji.kunectron.Kunectron
import com.tksimeji.gonunne.command.BrigadierCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.azisaba.vanilife.gui.ItemListGui
import org.bukkit.entity.Player

object ItemListCommand: BrigadierCommand {
    override val description: String = "Open the item list."

    override val aliases: Set<String> = setOf("il")

    override fun create(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("itemlist")
            .requires { it.sender.hasPermission("vanilife.commands.items") && it.sender is Player }
            .executes { ctx ->
                Kunectron.create(ItemListGui(ctx.source.sender as Player))
                return@executes Command.SINGLE_SUCCESS
            }
            .then(Commands.argument("query", StringArgumentType.greedyString())
                .executes { ctx ->
                    Kunectron.create(ItemListGui(ctx.source.sender as Player, query = ctx.getArgument("query", String::class.java)))
                    return@executes Command.SINGLE_SUCCESS
                })
    }
}