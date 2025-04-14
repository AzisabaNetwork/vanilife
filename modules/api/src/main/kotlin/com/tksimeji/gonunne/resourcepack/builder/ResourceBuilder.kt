package com.tksimeji.gonunne.resourcepack.builder

import com.tksimeji.gonunne.resourcepack.BuildInfo

interface ResourceBuilder<T> {
    val resourceClass: Class<T>

    fun build(info: BuildInfo, resource: T)
}