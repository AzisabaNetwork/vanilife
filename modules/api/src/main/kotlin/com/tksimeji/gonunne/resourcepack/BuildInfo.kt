package com.tksimeji.gonunne.resourcepack

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.kyori.adventure.key.KeyPattern
import java.io.File

class BuildInfo(val root: File, private val gson: Gson) {
    val assets: File
        get() = getDirectoryOrCreate(child = "assets")

    fun getFileOrCreate(parent: File = root, children: String): File {
        return File(parent, children).also {
            if (!it.exists()) {
                it.createNewFile()
            }
        }
    }

    fun getDirectoryOrCreate(parent: File = root, child: String): File {
        return File(parent, child).also {
            if (!it.exists()) {
                it.mkdir()
            }
        }
    }

    fun namespaceDirectory(@KeyPattern.Namespace namespace: String = "minecraft"): File {
        return getDirectoryOrCreate(assets, namespace)
    }

    fun createJsonFile(parent: File = root, child: String, json: Any): File {
        return createJsonFile(root, child, gson.toJsonTree(json))
    }

    fun createJsonFile(parent: File = root, child: String, json: JsonElement): File {
        val json2 = if (json is JsonObject) json.sorted() else json
        return getFileOrCreate(parent, child).apply { writeText(gson.toJson(json2)) }
    }
}