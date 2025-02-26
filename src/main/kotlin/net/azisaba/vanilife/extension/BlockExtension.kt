package net.azisaba.vanilife.extension

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.block.CustomBlockType
import net.azisaba.vanilife.registry.BlockTypes
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockType

var Block.customBlockType: CustomBlockType?
    get() {
        Vanilife.dataSource.connection.use { connection ->
            connection.prepareStatement("SELECT type FROM block WHERE world = ? AND x = ? AND y = ? AND z = ?").use { preparedStatement ->
                preparedStatement.setString(1, world.key.asString())
                preparedStatement.setInt(2, x)
                preparedStatement.setInt(3, y)
                preparedStatement.setInt(4, z)

                preparedStatement.executeQuery().use { resultSet ->
                    if (! resultSet.next()) {
                        return null
                    }

                    return BlockTypes.get(resultSet.getString("type").toKey())
                }
            }
        }
    }
    set(value) {
        Vanilife.dataSource.connection.use { connection ->
            if (value == null) {
                connection.prepareStatement("DELETE FROM block WHERE world = ? AND x = ? AND y = ? AND z = ?").use { preparedStatement ->
                    preparedStatement.setString(1, world.key.asString())
                    preparedStatement.setInt(2, x)
                    preparedStatement.setInt(3, y)
                    preparedStatement.setInt(4, z)

                    preparedStatement.executeUpdate()
                }
            } else {
                connection.prepareStatement("INSERT INTO block VALUES(?, ?, ?, ?, ?)").use { preparedStatement ->
                    preparedStatement.setString(1, value.key.asString())
                    preparedStatement.setString(2, world.key.asString())
                    preparedStatement.setInt(3, x)
                    preparedStatement.setInt(4, y)
                    preparedStatement.setInt(5, z)

                    preparedStatement.executeUpdate()
                }
            }
        }
    }

val Block.isCustomBlock: Boolean
    get() = customBlockType != null

fun BlockType.toMaterial(): Material {
    return Material.matchMaterial(key.asString())!!
}