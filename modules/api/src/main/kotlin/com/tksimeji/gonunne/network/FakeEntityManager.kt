package com.tksimeji.gonunne.network

import com.github.retrooper.packetevents.util.Vector3d
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.UUID

internal object FakeEntityManager {
    private val fakeEntities = mutableMapOf<FakeEntity, FakeEntityInfo>()

    private val chunkIndex = mutableMapOf<Chunk, MutableSet<FakeEntity>>()
    private val worldIndex = mutableMapOf<World, MutableSet<FakeEntity>>()

    private data class FakeEntityInfo(val entityId: Int, val uniqueId: UUID, val location: Location)

    fun getEntityId(fakeEntity: FakeEntity): Int {
        if (!isRecognized(fakeEntity)) {
            throw IllegalArgumentException("Unrecognized fake entity.")
        }
        return fakeEntities[fakeEntity]!!.entityId
    }

    fun getUniqueId(fakeEntity: FakeEntity): UUID {
        if (!isRecognized(fakeEntity)) {
            throw IllegalArgumentException("Unrecognized fake entity.")
        }
        return fakeEntities[fakeEntity]!!.uniqueId
    }

    fun getLocation(fakeEntity: FakeEntity): Location {
        if (!isRecognized(fakeEntity)) {
            throw IllegalArgumentException("Unrecognized fake entity.")
        }
        return fakeEntities[fakeEntity]!!.location.clone()
    }

    fun setLocation(fakeEntity: FakeEntity, location: Location) {
        if (!isRecognized(fakeEntity)) {
            throw IllegalArgumentException("Unrecognized fake entity.")
        }
        val info = fakeEntities[fakeEntity]!!
        removeFromIndex(fakeEntity)
        fakeEntities[fakeEntity] = FakeEntityInfo(info.entityId, info.uniqueId, location)
        addToIndex(fakeEntity)
    }

    fun <T: FakeEntity> addFakeEntity(location: Location, fakeEntity: T): T {
        if (isRecognized(fakeEntity)) {
            throw IllegalArgumentException("Already recognized fake entity.")
        }
        val entityId = Bukkit.getUnsafe().nextEntityId()
        fakeEntities[fakeEntity] = FakeEntityInfo(entityId, UUID.randomUUID(), location)
        addToIndex(fakeEntity)
        for (player in location.chunk.playersSeeingChunk) {
            sendSpawnPacket(player, fakeEntity)
        }
        return fakeEntity
    }

    fun removeFakeEntity(fakeEntity: FakeEntity) {
        if (!isRecognized(fakeEntity)) {
            throw IllegalArgumentException("Unrecognized fake entity.")
        }
        fakeEntities.remove(fakeEntity)
        removeFromIndex(fakeEntity)
    }

    fun isRecognized(fakeEntity: FakeEntity): Boolean {
        return fakeEntities.containsKey(fakeEntity)
    }

    fun sendSpawnPacket(player: Player, fakeEntity: FakeEntity) {
        val packet = WrapperPlayServerSpawnEntity(fakeEntity.entityId,
            fakeEntity.uniqueId,
            fakeEntity.entityType.toPacketEventsEntityType(),
            fakeEntity.location.toPacketEventsLocation(),
            0f,
            0,
            Vector3d.zero())
        player.sendPacketSilently(packet)
    }

    fun lookup(chunk: Chunk): Set<FakeEntity> {
        return chunkIndex[chunk]?.toSet() ?: emptySet()
    }

    fun lookup(world: World): Set<FakeEntity> {
        return worldIndex[world]?.toSet() ?: emptySet()
    }

    private fun addToIndex(fakeEntity: FakeEntity) {
        val location = getLocation(fakeEntity)
        val chunk = location.chunk
        val world = location.world

        chunkIndex.putIfAbsent(chunk, mutableSetOf())
        chunkIndex[chunk]!!.add(fakeEntity)

        worldIndex.putIfAbsent(world, mutableSetOf())
        worldIndex[world]!!.add(fakeEntity)
    }

    private fun removeFromIndex(fakeEntity: FakeEntity) {
        val location = getLocation(fakeEntity)
        val chunk = location.chunk
        val world = location.world

        chunkIndex[chunk]?.remove(fakeEntity)
        if (lookup(chunk).isEmpty()) chunkIndex.remove(chunk)

        worldIndex[world]?.remove(fakeEntity)
        if (lookup(world).isEmpty()) worldIndex.remove(world)
    }
}