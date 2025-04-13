package net.azisaba.vanilife.gui

import com.tksimeji.kunectron.AnvilGui
import com.tksimeji.kunectron.element.Element
import com.tksimeji.kunectron.event.AnvilGuiEvents
import com.tksimeji.kunectron.event.GuiHandler
import com.tksimeji.kunectron.hooks.AnvilGuiHooks
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemType

@AnvilGui(overwriteResultSlot = false)
class SearchGui(@AnvilGui.Player private val player: Player, private val target: Searchable): AnvilGuiHooks {
    @AnvilGui.FirstElement
    private val firstElement = Element.item(ItemType.PAPER)

    @GuiHandler
    fun onClose(event: AnvilGuiEvents.CloseEvent) {
        target.search(event.text.ifBlank { null })
    }

    @GuiHandler
    fun onClick(event: AnvilGuiEvents.ClickEvent) {
        if (event.isResultSlot) {
            useClose()
        }
    }
}