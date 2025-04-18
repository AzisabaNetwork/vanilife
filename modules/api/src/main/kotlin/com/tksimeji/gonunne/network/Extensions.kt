package com.tksimeji.gonunne.network

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.*

val Chunk.fakeEntities: Set<FakeEntity>
    get() = FakeEntityManager.lookup(this)

val FakeEntity.entityId: Int
    get() = FakeEntityManager.getEntityId(this)

val FakeEntity.uniqueId: UUID
    get() = FakeEntityManager.getUniqueId(this)

val FakeEntity.location: Location
    get() = FakeEntityManager.getLocation(this)

val FakeEntity.isSpawned: Boolean
    get() = FakeEntityManager.isRecognized(this)

val World.fakeEntities: Set<FakeEntity>
    get() = FakeEntityManager.lookup(this)

fun EntityType.toPacketEventsEntityType(): com.github.retrooper.packetevents.protocol.entity.type.EntityType {
    return SpigotConversionUtil.fromBukkitEntityType(this)
}

fun FakeEntity.remove() {
    FakeEntityManager.removeFakeEntity(this)
}

fun Location.toPacketEventsLocation(): com.github.retrooper.packetevents.protocol.world.Location {
    return SpigotConversionUtil.fromBukkitLocation(this)
}

fun Player.sendPacket(packet: PacketWrapper<*>) {
    PacketEvents.getAPI().playerManager.sendPacket(this, packet)
}

fun Player.sendPacketSilently(packet: PacketWrapper<*>) {
    PacketEvents.getAPI().playerManager.sendPacketSilently(this, packet)
}

fun Player.sendFakeEntity(fakeEntity: FakeEntity) {
    FakeEntityManager.sendSpawnPacket(this, fakeEntity)
}

fun <T: FakeEntity> World.spawnFakeEntity(location: Location, fakeEntity: T): T {
    return FakeEntityManager.addFakeEntity(location, fakeEntity)
}