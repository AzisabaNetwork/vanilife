package net.azisaba.vanilife.block

import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes
import com.github.retrooper.packetevents.util.Vector3d
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.packetevents
import net.azisaba.vanilife.item.BlockItemType
import net.azisaba.vanilife.registry.ItemTypes
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockType
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import java.util.*

object ChestoreBlockType: CustomBlockType, Tickable {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "chestore")

    override val type: BlockType = BlockType.CHEST

    override val itemType: BlockItemType
        get() = ItemTypes.CHESTORE

    private val viewers = mutableMapOf<Location, Set<Player>>()

    override fun onPlace(block: Block) {
    }

    override fun onBreak(block: Block) {
    }

    override fun tick(block: Block) {
        val chest = block.state as Chest
        val chestInventory = chest.inventory
        val itemStack = chestInventory.filterNotNull().firstOrNull()

        val location = block.location.add(0.5, 0.5, 0.5)

        for (player in Bukkit.getOnlinePlayers()) {
            val spawnPacket = WrapperPlayServerSpawnEntity(player.world.entityCount + 1,
                UUID.randomUUID(),
                EntityTypes.ITEM,
                location.packetevents(),
                0.0F,
                0,
                Vector3d.zero())

            // player.sendPacket(spawnPacket)

            val metaPacket = WrapperPlayServerEntityMetadata(
                spawnPacket.entityId,
                listOf(EntityData(8,
                    EntityDataTypes.ITEMSTACK,
                    itemStack?.packetevents())
            ))

            // player.sendPacket(metaPacket)
        }
    }
}