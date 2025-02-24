package net.azisaba.vanilife.gui

import com.tksimeji.visualkit.ChestUI
import com.tksimeji.visualkit.api.Asm
import com.tksimeji.visualkit.api.Policy
import com.tksimeji.visualkit.api.Size
import com.tksimeji.visualkit.policy.PolicyTarget
import com.tksimeji.visualkit.policy.SlotPolicy
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class TrashGUI(player: Player): ChestUI(player) {
    @Policy(asm = [Asm(from = 0, to = 53)])
    private val guiPolicy = SlotPolicy.VARIATION

    @Policy(asm = [Asm(from = 0, to = 40)], target = PolicyTarget.INVENTORY)
    private val inventoryPolicy = SlotPolicy.VARIATION

    override fun title(): Component {
        return Component.translatable("ごみばこ")
    }

    override fun size(): Size {
        return Size.SIZE_54
    }

    override fun onClose() {
        if (inventory.isEmpty) {
            return
        }

        ConfirmGUI(player, onReject = {
            for (content in inventory.contents.filterNotNull()) {
                player.inventory.addItem(content)
            }
        })
    }
}