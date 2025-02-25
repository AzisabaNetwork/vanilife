package net.azisaba.vanilife.command

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import io.papermc.paper.command.brigadier.argument.CustomArgumentType
import net.azisaba.vanilife.extension.CommandSyntaxException
import net.azisaba.vanilife.vwm.Season
import java.util.concurrent.CompletableFuture

object SeasonArgumentType: CustomArgumentType<Season, String> {
    override fun getNativeType(): ArgumentType<String> {
        return StringArgumentType.word()
    }

    override fun parse(reader: StringReader): Season {
        val input = nativeType.parse(reader)

        if (Season.entries.none { it.name.equals(input, ignoreCase = true) }) {
            throw CommandSyntaxException("'$input' is an invalid season.")
        }

        return Season.valueOf(input.uppercase())
    }

    override fun <S : Any> listSuggestions(ctx: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return CompletableFuture<Suggestions>().completeAsync {
            for (season in Season.entries) {
                builder.suggest(season.name.lowercase())
            }

            builder.build()
        }
    }
}