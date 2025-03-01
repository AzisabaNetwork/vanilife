package net.azisaba.vanilife.registry

import net.azisaba.vanilife.extension.toMaterial
import net.azisaba.vanilife.portal.CaveworldPortal
import net.azisaba.vanilife.portal.Portal
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Portals: KeyedRegistry<Portal>() {
    val CAVEWORLD = register(CaveworldPortal)

    fun get(item: ItemStack, fluidType: Material, frameType: Material): Collection<Portal> {
        return getByFluidType(fluidType).intersect(getByFrameType(frameType).toSet()).filter { it.item == item }
    }

    fun getByItemStack(itemStack: ItemStack): Collection<Portal> {
        return values.filter { it.item == itemStack }.toSet()
    }

    fun getByFluidType(fluidType: Material): Collection<Portal> {
        return values.filter { it.fluidType.toMaterial() == fluidType }.toSet()
    }

    fun getByFrameType(frameType: Material): Collection<Portal> {
        return values.filter { it.frameType.toMaterial() == frameType }.toSet()
    }
}