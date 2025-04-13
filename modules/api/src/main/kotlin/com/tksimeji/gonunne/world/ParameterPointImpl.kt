package com.tksimeji.gonunne.world

import com.tksimeji.gonunne.Range

internal class ParameterPointImpl: ParameterPoint {
    private var temperature = Range(0f, 0f)

    private var humidity = Range(0f, 0f)

    private var continentalness = Range(0f, 0f)

    private var erosion = Range(0f, 0f)

    private var depth = Range(0f, 0f)

    private var weirdness = Range(0f, 0f)

    private var offset = 0L

    override fun temperature(): Range<Float> {
        return temperature
    }

    override fun temperature(temperature: Float): ParameterPoint {
        return temperature(temperature, temperature)
    }

    override fun temperature(min: Float, max: Float): ParameterPoint {
        temperature = Range(min, max)
        return this
    }

    override fun humidity(): Range<Float> {
        return humidity
    }

    override fun humidity(humidity: Float): ParameterPoint {
        return humidity(humidity, humidity)
    }

    override fun humidity(min: Float, max: Float): ParameterPoint {
        humidity = Range(min, max)
        return this
    }

    override fun continentalness(): Range<Float> {
        return continentalness
    }

    override fun continentalness(continentalness: Float): ParameterPoint {
        return continentalness(continentalness, continentalness)
    }

    override fun continentalness(min: Float, max: Float): ParameterPoint {
        continentalness = Range(min, max)
        return this
    }

    override fun erosion(): Range<Float> {
        return erosion
    }

    override fun erosion(erosion: Float): ParameterPoint {
        return erosion(erosion, erosion)
    }

    override fun erosion(min: Float, max: Float): ParameterPoint {
        erosion = Range(min, max)
        return this
    }

    override fun depth(): Range<Float> {
        return depth
    }

    override fun depth(depth: Float): ParameterPoint {
        return depth(depth, depth)
    }

    override fun depth(min: Float, max: Float): ParameterPoint {
        depth = Range(min, max)
        return this
    }

    override fun weirdness(): Range<Float> {
        return weirdness
    }

    override fun weirdness(weirdness: Float): ParameterPoint {
        return weirdness(weirdness, weirdness)
    }

    override fun weirdness(min: Float, max: Float): ParameterPoint {
        weirdness = Range(min, max)
        return this
    }

    override fun offset(): Long {
        return offset
    }

    override fun offset(offset: Long): ParameterPoint {
        this.offset = offset
        return this
    }
}