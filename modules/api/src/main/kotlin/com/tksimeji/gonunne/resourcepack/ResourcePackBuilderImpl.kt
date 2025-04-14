package com.tksimeji.gonunne.resourcepack

import com.google.gson.GsonBuilder
import com.tksimeji.gonunne.component.jsonText
import com.tksimeji.gonunne.resourcepack.builder.ResourceBuilder
import com.tksimeji.gonunne.resourcepack.data.Pack
import com.tksimeji.gonunne.resourcepack.data.PackMcmeta
import net.kyori.adventure.text.Component
import java.io.File
import java.util.*

internal class ResourcePackBuilderImpl: ResourcePackBuilder {
    private var packFormat = 0

    private var description: Component = Component.empty()

    private val resources = mutableSetOf<Any>()
    private val resourceBuilders = mutableSetOf<ResourceBuilder<*>>()

    override fun packFormat(packFormat: Int): ResourcePackBuilder {
        this.packFormat = packFormat
        return this
    }

    override fun description(description: Component): ResourcePackBuilder {
        this.description = description
        return this
    }

    override fun lang(lang: ResourceBundle): ResourcePackBuilder {
        return resource(lang)
    }

    override fun resource(resource: Any): ResourcePackBuilder {
        resources.add(resource)
        return this
    }

    override fun resourceBuilder(resourceBuilder: ResourceBuilder<*>): ResourcePackBuilder {
        resourceBuilders.add(resourceBuilder)
        return this
    }

    override fun build(out: File, name: String): File {
        out.deleteContents()
        val root = File(out, name).also { it.mkdirs() }
        val info = BuildInfo(root, gson)

        val packMcmeta = PackMcmeta(Pack(packFormat = packFormat, description = description.jsonText()))
        info.createJsonFile(child = "pack.mcmeta", json = packMcmeta)

        for (resource in resources) {
            lookup(resource).build(info, resource)
        }
        return root
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T: Any> lookup(resource: T): ResourceBuilder<T> {
        val clazz = resource::class.java
        return resourceBuilders.first { it.resourceClass.isAssignableFrom(clazz) } as ResourceBuilder<T>
    }

    companion object {
        private val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()
    }
}