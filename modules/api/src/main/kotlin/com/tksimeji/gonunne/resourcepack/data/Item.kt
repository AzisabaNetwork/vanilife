package com.tksimeji.gonunne.resourcepack.data

internal data class Item(val model: Model) {
    internal data class Model(
        val type: String,
        val model: String
    )
}