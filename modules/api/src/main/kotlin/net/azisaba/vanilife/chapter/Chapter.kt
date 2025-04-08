package net.azisaba.vanilife.chapter

import net.azisaba.vanilife.registry.IKeyedRegistry
import net.azisaba.vanilife.registry.Keyed
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component

interface Chapter: IKeyedRegistry<Objective>, Keyed {
    val icon: Key

    val title: Component

    val description: Component
}