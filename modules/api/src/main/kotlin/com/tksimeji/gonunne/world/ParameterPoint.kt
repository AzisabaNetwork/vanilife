package com.tksimeji.gonunne.world

import com.tksimeji.gonunne.Range

interface ParameterPoint {
    fun temperature(): Range<Float>

    fun temperature(temperature: Float): ParameterPoint

    fun temperature(min: Float, max: Float): ParameterPoint

    fun humidity(): Range<Float>

    fun humidity(humidity: Float): ParameterPoint

    fun humidity(min: Float, max: Float): ParameterPoint

    fun continentalness(): Range<Float>

    fun continentalness(continentalness: Float): ParameterPoint

    fun continentalness(min: Float, max: Float): ParameterPoint

    fun erosion(): Range<Float>

    fun erosion(erosion: Float): ParameterPoint

    fun erosion(min: Float, max: Float): ParameterPoint

    fun depth(): Range<Float>

    fun depth(depth: Float): ParameterPoint

    fun depth(min: Float, max: Float): ParameterPoint

    fun weirdness(): Range<Float>

    fun weirdness(weirdness: Float): ParameterPoint

    fun weirdness(min: Float, max: Float): ParameterPoint

    fun offset(): Long

    fun offset(offset: Long): ParameterPoint

    companion object {
        @JvmStatic
        fun parameterPoint(): ParameterPoint {
            return ParameterPointImpl()
        }
    }
}