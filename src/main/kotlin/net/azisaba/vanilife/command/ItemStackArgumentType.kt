package net.azisaba.vanilife.command

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.CustomArgumentType
import net.azisaba.vanilife.extension.ItemStack
import net.azisaba.vanilife.item.Item
import net.azisaba.vanilife.registry.Items
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
        val itemTypes = Registry.ITEM.union(Items.values)
        val itemType = itemTypes.firstOrNull { it.key() == input } ?: throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create()

        if (itemType is ItemType) {
            return itemType.createItemStack()
        } else if (itemType is Item) {
            return ItemStack(itemType)
        }

        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create()
    }

    override fun <S : Any> listSuggestions(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return CompletableFuture.supplyAsync {
            for (suggestion in ArgumentTypes.itemStack().listSuggestions(context, builder).get().list) {
                builder.suggest(suggestion.text)
            }

            val input = builder.remainingLowerCase

            for (item in Items.values.filter { it.key.namespace().startsWith(input) || it.key.value().startsWith(input) }) {
                builder.suggest(item.key.asString())
            }

            builder.build()
        }
    }
}