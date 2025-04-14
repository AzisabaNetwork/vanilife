package com.tksimeji.gonunne.resourcepack

import com.tksimeji.gonunne.resourcepack.builder.LangResourceBuilder
import com.tksimeji.gonunne.resourcepack.builder.ResourceBuilder
import net.kyori.adventure.key.KeyPattern
import net.kyori.adventure.text.Component
import org.jetbrains.annotations.ApiStatus
import java.io.File
import java.util.ResourceBundle

@ApiStatus.Experimental
interface ResourcePackBuilder {
    fun packFormat(packFormat: Int): ResourcePackBuilder

    fun description(description: Component): ResourcePackBuilder

    fun lang(lang: ResourceBundle): ResourcePackBuilder

    fun resource(resource: Any): ResourcePackBuilder

    fun resourceBuilder(resourceBuilder: ResourceBuilder<*>): ResourcePackBuilder

    fun build(out: File, @KeyPattern name: String): File

    companion object {
        fun resourcePackBuilder(): ResourcePackBuilder {
            return ResourcePackBuilderImpl().resourceBuilder(LangResourceBuilder)
        }
    }
}