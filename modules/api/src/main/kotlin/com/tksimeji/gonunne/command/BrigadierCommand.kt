package com.tksimeji.gonunne.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack

interface BrigadierCommand {
    val description: String?
        get() = null

    val aliases: Set<String>
        get() = setOf()

    fun create(): LiteralArgumentBuilder<CommandSourceStack>
}