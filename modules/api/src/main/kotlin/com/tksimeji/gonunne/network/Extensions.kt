package com.tksimeji.gonunne.network

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

fun EntityType.toPacketEventsEntityType(): com.github.retrooper.packetevents.protocol.entity.type.EntityType {
    return SpigotConversionUtil.fromBukkitEntityType(this)
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