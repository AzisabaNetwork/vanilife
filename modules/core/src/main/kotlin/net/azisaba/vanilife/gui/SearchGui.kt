package net.azisaba.vanilife.gui

import com.tksimeji.kunectron.SignGui
import com.tksimeji.kunectron.event.GuiHandler
import com.tksimeji.kunectron.event.SignGuiEvents
import com.tksimeji.kunectron.hooks.AnvilGuiHooks
import org.bukkit.entity.Player

@SignGui
class SearchGui(@SignGui.Player private val player: Player, private val target: Searchable): AnvilGuiHooks {
    @GuiHandler
    fun onClose(event: SignGuiEvents.CloseEvent) {
        target.search(event.firstLine.ifBlank { null })
    }
}