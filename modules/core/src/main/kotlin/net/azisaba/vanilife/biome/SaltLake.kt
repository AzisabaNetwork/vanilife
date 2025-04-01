package net.azisaba.vanilife.biome

import net.azisaba.vanilife.Vanilife
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextColor

object SaltLake: CustomBiome {
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "salt_lake")

    override val hasPrecipitation: Boolean = false

    override val temperature: Float = 2.0F

    override val downfall: Float = 0.0F

    override val effects: CustomBiome.Effects = CustomBiome.Effects(
        TextColor.color(192, 216, 255),
        TextColor.color(63, 118, 288),
        TextColor.color(5, 5, 51),
        TextColor.color(110, 117, 255)
    )

    override val spawners: List<CustomBiome.Spawner> = emptyList()
}