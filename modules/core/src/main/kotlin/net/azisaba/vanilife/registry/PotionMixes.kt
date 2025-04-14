package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.item.createItemStack
import com.tksimeji.gonunne.key.toNamespacedKey
import com.tksimeji.gonunne.potion.createItemStack
import com.tksimeji.gonunne.registry.impl.KeyedRegistryImpl
import io.papermc.paper.potion.PotionMix
import net.azisaba.vanilife.PLUGIN_ID
import net.kyori.adventure.key.Key
import org.bukkit.inventory.RecipeChoice
import org.bukkit.potion.PotionType

object PotionMixes: KeyedRegistryImpl<PotionMix>() {
    val LONG_FIRE_RESISTANCE = register(PotionMix(
        Key.key(PLUGIN_ID, "long_fire_resistance").toNamespacedKey(),
        PotionType.LONG_FIRE_RESISTANCE.createItemStack(),
        RecipeChoice.ExactChoice(PotionType.AWKWARD.createItemStack()),
        RecipeChoice.ExactChoice(CustomItemTypes.REFINED_MAGMA_CREAM.createItemStack())
    ))
}