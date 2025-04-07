package net.azisaba.vanilife.registry

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.extension.createItemStack
import net.azisaba.vanilife.fish.Fish
import net.azisaba.vanilife.fish.SimpleFish
import net.azisaba.vanilife.fish.VanillaFish
import net.kyori.adventure.key.Key

object Fishes: KeyedRegistry<Fish>() {
    val TUNA = register(SimpleFish(
        Key.key(Vanilife.PLUGIN_ID, "tuna"),
        1,
        CustomItemTypes.TUNA.createItemStack(),
        0.1,
        1.0,
        3.0)
    )

    val VANILLA = register(VanillaFish)
}