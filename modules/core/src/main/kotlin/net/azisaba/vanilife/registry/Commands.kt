package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.command.BrigadierCommand
import com.tksimeji.gonunne.registry.impl.RegistryImpl
import net.azisaba.vanilife.command.DialogueCommand
import net.azisaba.vanilife.command.ItemListCommand

object Commands: RegistryImpl<String, BrigadierCommand>() {
    val DIALOGUE = register(DialogueCommand)

    val ITEM_LIST = register(ItemListCommand)

    fun <T: BrigadierCommand> register(commandCreator: T): T {
        return register(commandCreator.create().literal, commandCreator)
    }
}