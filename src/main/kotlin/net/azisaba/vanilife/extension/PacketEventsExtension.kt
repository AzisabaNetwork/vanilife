package net.azisaba.vanilife.extension

import com.github.retrooper.packetevents.protocol.world.WorldBlockPosition
import com.github.retrooper.packetevents.resources.ResourceLocation
import com.github.retrooper.packetevents.util.Vector3i
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block

fun ResourceLocation.toKey(): Key {
    return Key.key(namespace, key)
}

fun Vector3i.toBlock(world: World): Block {
    return world.getBlockAt(x, y, z)
}

fun WorldBlockPosition.toBlock(): Block {
    return toLocation().block
}

fun WorldBlockPosition.toLocation(): Location {
    return Location(Bukkit.getWorld(world.toKey()),
        blockPosition.x.toDouble(),
        blockPosition.y.toDouble(),
        blockPosition.z.toDouble())
}