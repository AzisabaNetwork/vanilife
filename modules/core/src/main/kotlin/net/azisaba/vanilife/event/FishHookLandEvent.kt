package net.azisaba.vanilife.event

import io.papermc.paper.block.fluid.FluidData
import org.bukkit.entity.FishHook
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

class FishHookLandEvent(val hook: FishHook, val player: Player, val fluidData: FluidData, val fishingRod: ItemStack): Event() {
    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }

    companion object {
        private val HANDLER_LIST = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLER_LIST
        }
    }
}