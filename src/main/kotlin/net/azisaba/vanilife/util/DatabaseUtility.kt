package net.azisaba.vanilife.util

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.text.Component

fun createTableIfNotExists(name: String, sql: String) {
    Vanilife.dataSource.connection.use { connection ->
        connection.prepareStatement(if (sql.startsWith(':')) "CREATE TABLE IF NOT EXISTS $name (${sql.substring(1)})" else sql).use { preparedStatement ->
            preparedStatement.executeUpdate()
            Vanilife.logger.info(Component.text("Created '$name' table in the database."))
        }
    }
}