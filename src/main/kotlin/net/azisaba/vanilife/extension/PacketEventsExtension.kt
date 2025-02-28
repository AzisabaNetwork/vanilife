package net.azisaba.vanilife.extension

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.resources.ResourceLocation
import com.github.retrooper.packetevents.util.Vector3d
import com.github.retrooper.packetevents.util.Vector3i
import com.github.retrooper.packetevents.wrapper.PacketWrapper
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun Player.sendPacket(packet: PacketWrapper<*>) {
    PacketEvents.getAPI().playerManager.sendPacket(this, packet)
}

fun ItemStack.packetevents(): com.github.retrooper.packetevents.protocol.item.ItemStack {
    return SpigotConversionUtil.fromBukkitItemStack(this)
}

fun Location.packetevents(): com.github.retrooper.packetevents.protocol.world.Location {
    return com.github.retrooper.packetevents.protocol.world.Location(Vector3d(x, y, z), yaw, pitch)
}

fun ResourceLocation.paper(): Key {
    return Key.key(namespace, key)
}

fun Vector3i.paper(world: World): Block {
    return world.getBlockAt(x, y, z)
}