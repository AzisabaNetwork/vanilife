package com.tksimeji.gonunne.resourcepack

import com.google.gson.JsonObject
import java.io.File

internal fun File.deleteContents() {
    if (!exists() || !isDirectory) {
        return
    }
    listFiles()?.forEach { file ->
        if (file.isDirectory) {
            file.deleteContents()
        } else {
            file.delete()
        }
    }
}

fun JsonObject.sorted(): JsonObject {
    val sortedMap = entrySet()
        .sortedWith(compareBy { if (it.key == "type") "" else it.key })
        .associate { it.key to it.value }
    val sortedJsonObject = JsonObject()
    for ((key, value) in sortedMap) {
        sortedJsonObject.add(key, value)
    }
    return sortedJsonObject
}