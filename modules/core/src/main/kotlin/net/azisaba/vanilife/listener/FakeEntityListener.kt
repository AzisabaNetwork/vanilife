package net.azisaba.vanilife.listener

import com.tksimeji.gonunne.network.fakeEntities
import com.tksimeji.gonunne.network.sendFakeEntity
import io.papermc.paper.event.packet.PlayerChunkLoadEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object FakeEntityListener: Listener {
    @EventHandler
    fun onPlayerChunkLoad(event: PlayerChunkLoadEvent) {
        val player = event.player
        val chunk = event.chunk
        for (fakeEntity in chunk.fakeEntities) {
            player.sendFakeEntity(fakeEntity)
        }
    }
}