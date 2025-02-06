package net.azisaba.vanilife.util

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

val Player.hand: ItemStack
    get() = inventory.itemInMainHand