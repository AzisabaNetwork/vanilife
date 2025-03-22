package net.azisaba.vanilife.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import net.azisaba.vanilife.command.argument.ItemStackArgumentType
import net.azisaba.vanilife.extension.customItemType
import net.azisaba.vanilife.extension.hasCustomItemType
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.inventory.ItemStack

object GiveCommand: CommandCreator {
    override fun create(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("give")
            .then(Commands.argument("targets", ArgumentTypes.players())
                .then(Commands.argument("item", ItemStackArgumentType)
                    .executes(this::giveCommand)
                    .then(Commands.argument("count", IntegerArgumentType.integer(1))
                        .executes { ctx -> giveCommand(ctx, ctx.getArgument("count", Int::class.java)) })))
    }

    private fun giveCommand(ctx: CommandContext<CommandSourceStack>, amount: Int = 1): Int {
        val targets = ctx.getArgument("targets", PlayerSelectorArgumentResolver::class.java).resolve(ctx.source)
        val itemStack = ctx.getArgument("item", ItemStack::class.java).apply { this.amount = amount }

        for (target in targets) {
            target.give(itemStack)
        }

        val sender = ctx.source.sender
        val itemTypeName = Component.text().append(Component.text("["))
            .append(if (itemStack.hasCustomItemType()) itemStack.customItemType!!.displayName else Component.translatable(itemStack))
            .append(Component.text("]"))

        if (1 < targets.size) {
            sender.sendMessage(Component.translatable("commands.give.success.multiple",
                Component.text(amount),
                itemTypeName,
                Component.text(targets.size)))
        } else if (targets.size == 1) {
            sender.sendMessage(Component.translatable("commands.give.success.single",
                Component.text(amount),
                itemTypeName,
                Component.text(targets.first().name)))
        } else {
            sender.sendMessage(Component.translatable("argument.entity.notfound.player").color(NamedTextColor.RED))
        }

        return Command.SINGLE_SUCCESS
    }
}