package net.azisaba.vanilife.extension

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.block.CustomBlockType
import net.azisaba.vanilife.registry.BlockTypes
import net.azisaba.vanilife.vwm.Cluster
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.block.Block

val customBlockMap: MutableMap<Block, CustomBlockType> by lazy {
    val map = mutableMapOf<Block, CustomBlockType>()

    Vanilife.dataSource.connection.use { connection ->
        connection.prepareStatement("SELECT type, world, x, y, z FROM block").use { preparedStatement ->
            preparedStatement.executeQuery().use { resultSet ->
                while (resultSet.next()) {
                    val type = BlockTypes.get(resultSet.getString("type").toKey()) ?: continue
                    val world = Bukkit.getWorld(resultSet.getString("world").toKey()) ?: continue
                    val x = resultSet.getInt("x")
                    val y = resultSet.getInt("y")
                    val z = resultSet.getInt("z")
                    map[world.getBlockAt(x, y, z)] = type
                }
            }
        }
    }

    map
}

val World.cluster: Cluster?
    get() = Cluster.all().firstOrNull { it.contains(this) }

fun World.hasCluster(): Boolean {
    return cluster != null
}

fun World.getCustomBlocks(type: CustomBlockType, loadedChunkOnly: Boolean = false): Collection<Block> {
    return customBlockMap.filter {
        it.value == type && it.key.type == type.type.toMaterial() && (! loadedChunkOnly || it.key.location.isChunkLoaded)
    }.keys
}