package com.tksimeji.gonunne.resourcepack.data

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

internal data class PackMcmeta(val pack: Pack)

internal data class Pack(
    @SerializedName("pack_format") val packFormat: Int,
    val description: JsonElement
)
