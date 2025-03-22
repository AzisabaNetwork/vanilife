package net.azisaba.vanilife.command.argument

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.CustomArgumentType
import net.azisaba.vanilife.item.CustomItemType
import net.azisaba.vanilife.registry.CustomItemTypes
import net.kyori.adventure.key.Key
import org.bukkit.Registry
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import java.util.concurrent.CompletableFuture

object ItemStackArgumentType: CustomArgumentType<ItemStack, Key> {
    override fun getNativeType(): ArgumentType<Key> {
        return ArgumentTypes.key()
    }

    override fun parse(reader: StringReader): ItemStack {
        val input = nativeType.parse(reader)
        val itemTypes = Registry.ITEM.union(CustomItemTypes.values)
        val itemType = itemTypes.firstOrNull { it.key() == input } ?: throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create()

        if (itemType is ItemType) {
            return itemType.createItemStack()
        } else if (itemType is CustomItemType) {
            return itemType.createItemStack()
        }

        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create()
    }

    override fun <S : Any> listSuggestions(ctx: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return CompletableFuture.supplyAsync {
            for (suggestion in ArgumentTypes.itemStack().listSuggestions(ctx, builder).get().list) {
                builder.suggest(suggestion.text)
            }

            val input = builder.remainingLowerCase

            for (item in CustomItemTypes.values.filter { it.key.asString().startsWith(input, ignoreCase = true) || it.key.namespace().startsWith(input, ignoreCase = true) || it.key.value().startsWith(input, ignoreCase = true) }) {
                builder.suggest(item.key.asString())
            }

            builder.build()
        }
    }
}