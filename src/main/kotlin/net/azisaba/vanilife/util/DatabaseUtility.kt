package net.azisaba.vanilife.util

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.text.Component

fun createTableIfNotExists(name: String, sql: String) {
    Vanilife.dataSource.connection.use { connection ->
        val metaData = connection.metaData
        val resultSet = metaData.getTables(null, null, name, null)

        if (! resultSet.next()) {
            connection.createStatement().use { statement ->
                statement.execute(if (sql.startsWith(':')) "CREATE TABLE $name (${sql.substring(1)})" else sql)
                Vanilife.pluginLogger.info(Component.text("Created '$name' table in the database."))
            }
        }
    }
}