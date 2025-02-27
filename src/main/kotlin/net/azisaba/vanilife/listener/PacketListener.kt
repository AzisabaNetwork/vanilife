package net.azisaba.vanilife.listener

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockChange
import net.azisaba.vanilife.extension.customBlockType
import net.azisaba.vanilife.extension.isCustomBlock
import net.azisaba.vanilife.extension.toBlock
import net.azisaba.vanilife.extension.toMaterial
import org.bukkit.Bukkit

object PacketListener: PacketListener {
    override fun onPacketSend(event: PacketSendEvent) {
        if (event.packetType == PacketType.Play.Server.BLOCK_CHANGE) {
            val packet = WrapperPlayServerBlockChange(event)
            val position = packet.blockPosition
            val player = Bukkit.getOnlinePlayers().first { it.uniqueId == event.user.uuid }
            val block = position.toBlock(player.world)

            if (block.isCustomBlock && block.type != block.customBlockType!!.type.toMaterial()) {
                block.customBlockType = null
            }
        }
    }
}