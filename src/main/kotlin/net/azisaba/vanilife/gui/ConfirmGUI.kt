package net.azisaba.vanilife.gui

import com.tksimeji.visualkit.ChestUI
import com.tksimeji.visualkit.api.Element
import com.tksimeji.visualkit.api.Handler
import com.tksimeji.visualkit.api.Size
import com.tksimeji.visualkit.element.VisualkitElement
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player

class ConfirmGUI(player: Player, onAccept: Runnable = Runnable {  }, onReject: Runnable = Runnable {  }) : ChestUI(player) {
    @Element(11)
    val accept = VisualkitElement.create(Material.GREEN_TERRACOTTA)
        .handler { -> onAccept.run() }

    @Element(15)
    val reject = VisualkitElement.create(Material.RED_TERRACOTTA)
        .handler { -> onReject.run() }

    @Handler(index = [11, 15])
    fun onInteract() {
        close()
    }

    override fun title(): Component {
        return Component.text("確認")
    }

    override fun size(): Size {
        return Size.SIZE_27
    }
}