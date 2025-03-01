package net.azisaba.vanilife.listener

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.block.PortalBlockType
import net.azisaba.vanilife.extension.customBlockType
import net.azisaba.vanilife.extension.dataStore
import net.azisaba.vanilife.extension.toKey
import net.azisaba.vanilife.registry.BlockTypes
import net.azisaba.vanilife.registry.Portals
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerPortalEvent
import org.bukkit.event.player.PlayerTeleportEvent

object PortalListener: Listener {
    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val item = event.itemDrop.takeIf { Portals.getByItemStack(it.itemStack).isNotEmpty() } ?: return

        Bukkit.getScheduler().runTaskLater(Vanilife.plugin, { ->
            val location = item.location
            val block = location.block.takeIf { it.type == Material.WATER } ?: return@runTaskLater

            fun createPortal(offsetX: Int, offsetY: Int, vararg fluids: Block) {
                val frameTypes = listOf(block.getRelative(0 + offsetX, 1, -1 + offsetY).type,
                    block.getRelative(-1 + offsetX, 1, -1 + offsetY).type,
                    block.getRelative(-1 + offsetX, 1, 0 + offsetY).type,
                    block.getRelative(-1 + offsetX, 1, 1 + offsetY).type,
                    block.getRelative(-1 + offsetX, 1, 2 + offsetY).type,
                    block.getRelative(0 + offsetX, 1, 2 + offsetY).type,
                    block.getRelative(1 + offsetX, 1, 2 + offsetY).type,
                    block.getRelative(2 + offsetX, 1, 2 + offsetY).type,
                    block.getRelative(2 + offsetX, 1, 1 + offsetY).type,
                    block.getRelative(2 + offsetX, 1, 0 + offsetY).type,
                    block.getRelative(2 + offsetX, 1, -1 + offsetY).type,
                    block.getRelative(1 + offsetX, 1, -1 + offsetY).type)

                if (frameTypes.distinct().size == 1) {
                    val portal = Portals.get(item.itemStack, fluids.first().type, frameTypes.first()).firstOrNull() ?: return
                    item.remove()

                    for (fluid in fluids) {
                        fluid.customBlockType = BlockTypes.PORTAL
                        fluid.dataStore = fluid.dataStore!!.apply {
                            addProperty("portal", portal.key.asString())
                        }
                    }

                    portal.onCreate(fluids.toSet())
                }
            }

            val block1 = block.getRelative(0, 0, 1)
            val block2 = block.getRelative(0, 0, -1)
            val block3 = block.getRelative(1, 0, 0)
            val block4 = block.getRelative(-1, 0, 0)
            val block5 = block.getRelative(1, 0, 1)
            val block6 = block.getRelative(-1, 0, -1)
            val block7 = block.getRelative(-1, 0, 1)
            val block8 = block.getRelative(1, 0, -1)

            if (block1.type == block3.type && block3.type == block5.type && Portals.getByFluidType(block1.type).isNotEmpty()) {
                createPortal(0, 0, block, block1, block3, block5)
            } else if (block1.type == block4.type && block4.type == block7.type && Portals.getByFluidType(block1.type).isNotEmpty()) {
                createPortal(-1, 0, block, block1, block4, block7)
            } else if (block2.type == block3.type && block3.type == block8.type && Portals.getByFluidType(block2.type).isNotEmpty()) {
                createPortal(0, -1, block, block2, block3, block8)
            } else if (block2.type == block4.type && block4.type == block6.type && Portals.getByFluidType(block2.type).isNotEmpty()) {
                createPortal(-1, -1, block, block2, block4, block6)
            }
        }, 15L)
    }

    @EventHandler
    fun onPlayerPortal(event: PlayerPortalEvent) {
        if (event.cause != PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            return
        }

        val block = event.from.block.takeIf { it.customBlockType is PortalBlockType } ?: return
        val portal = Portals.get(block.dataStore!!.get("portal").asString.toKey()) ?: return

        event.isCancelled = true
        portal.onPlayerPortal(event.player)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        val block1 = block.getRelative(1, 0, 0)
        val block2 = block.getRelative(0, 0, 1)
        val block3 = block.getRelative(-1, 0, 0)
        val block4 = block.getRelative(0, 0, -1)

        fun breakPortal(block: Block) {
            block.customBlockType = null
            block.breakNaturally()

            block.getRelative(0, 0, 1).takeIf { it.type == Material.END_PORTAL }?.apply {
                customBlockType = null
                breakNaturally()
            }
            block.getRelative(0, 0, -1).takeIf { it.type == Material.END_PORTAL }?.apply {
                customBlockType = null
                breakNaturally()
            }
            block.getRelative(1, 0, 0).takeIf { it.type == Material.END_PORTAL }?.apply {
                customBlockType = null
                breakNaturally()
            }
            block.getRelative(-1, 0, 0).takeIf { it.type == Material.END_PORTAL }?.apply {
                customBlockType = null
                breakNaturally()
            }
            block.getRelative(1, 0, 1).takeIf { it.type == Material.END_PORTAL }?.apply {
                customBlockType = null
                breakNaturally()
            }
            block.getRelative(-1, 0, -1).takeIf { it.type == Material.END_PORTAL }?.apply {
                customBlockType = null
                breakNaturally()
            }
            block.getRelative(-1, 0, 1).takeIf { it.type == Material.END_PORTAL }?.apply {
                customBlockType = null
                breakNaturally()
            }
            block.getRelative(1, 0, -1).takeIf { it.type == Material.END_PORTAL }?.apply {
                customBlockType = null
                breakNaturally()
            }
        }

        if (block1.type == Material.END_PORTAL) {
            breakPortal(block1)
        } else if (block2.type == Material.END_PORTAL) {
            breakPortal(block2)
        } else if (block3.type == Material.END_PORTAL) {
            breakPortal(block3)
        } else if (block4.type == Material.END_PORTAL) {
            breakPortal(block4)
        }
    }
}