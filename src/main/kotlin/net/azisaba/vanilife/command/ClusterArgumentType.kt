package net.azisaba.vanilife.command

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import io.papermc.paper.command.brigadier.argument.CustomArgumentType
import net.azisaba.vanilife.extension.CommandSyntaxException
import net.azisaba.vanilife.vwm.Cluster
import java.util.concurrent.CompletableFuture

object ClusterArgumentType: CustomArgumentType<Cluster, String> {
    override fun getNativeType(): ArgumentType<String> {
        return StringArgumentType.word()
    }

    override fun parse(reader: StringReader): Cluster {
        val input = nativeType.parse(reader)
        return Cluster.get(input) ?: throw CommandSyntaxException("Cluster '$input' was not found.")
    }

    override fun <S : Any> listSuggestions(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return CompletableFuture<Suggestions>().completeAsync {
            for (cluster in Cluster.instances) {
                builder.suggest(cluster.name)
            }

            builder.build()
        }
    }
}