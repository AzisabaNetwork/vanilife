package net.azisaba.vanilife.extension

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType

fun CommandSyntaxException(message: String = "Invalid syntax"): CommandSyntaxException {
    return SimpleCommandExceptionType(LiteralMessage(message)).create()
}