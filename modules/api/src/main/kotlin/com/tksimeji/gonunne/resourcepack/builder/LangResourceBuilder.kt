package com.tksimeji.gonunne.resourcepack.builder

import com.google.gson.JsonObject
import com.tksimeji.gonunne.resourcepack.BuildInfo
import java.util.ResourceBundle

internal object LangResourceBuilder: ResourceBuilder<ResourceBundle> {
    override val resourceClass: Class<ResourceBundle> = ResourceBundle::class.java

    private val regex = Regex("\\{(\\d+)}")

    override fun build(info: BuildInfo, resource: ResourceBundle) {
        val json = JsonObject()
        resource.keySet().forEach { key ->
            val value = resource.getString(key).replace(regex) { match ->
                val num = match.groupValues[1].toInt()
                val minecraftIndex = num + 1
                return@replace "%$minecraftIndex\$s"
            }
            json.addProperty(key, value)
        }
        info.createJsonFile(info.namespaceDirectory(), "${resource.locale.toString().lowercase()}.json", json)
    }
}