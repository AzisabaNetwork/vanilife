package net.azisaba.vanilife.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack

interface CommandCreator {
    fun create(): LiteralArgumentBuilder<CommandSourceStack>
}