package net.azisaba.vanilife

import net.azisaba.vanilife.adapter.Adapter
import net.azisaba.vanilife.biome.CustomBiome
import net.azisaba.vanilife.extension.nms
import net.azisaba.vanilife.extension.toResourceLocation
import net.azisaba.vanilife.util.mappedRegistry
import net.minecraft.core.Holder
import net.minecraft.core.MappedRegistry
import net.minecraft.core.RegistrationInfo
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Climate
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList

object V1_21_4: Adapter {
    fun a() {
        val overworldBiomeSource = MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD
        val point = Climate.parameters(
            Climate.Parameter.point(0.8F),
            Climate.Parameter.point(0.5F),
            Climate.Parameter.point(0.6F),
            Climate.Parameter.point(0.4F),
            Climate.Parameter.point(0.1F),
            Climate.Parameter.point(0.0F),
            0.0F
        )
    }

    override fun registerBiome(customBiome: CustomBiome) {
        val mappedRegistry = mappedRegistry(Registries.BIOME)

        val frozenField = MappedRegistry::class.java.getDeclaredField("frozen").apply { isAccessible = true }
        frozenField.set(mappedRegistry, false)

        val biome = customBiome.nms()
        val holder = mappedRegistry.register(ResourceKey.create(Registries.BIOME, customBiome.key.toResourceLocation()), biome, RegistrationInfo.BUILT_IN)

        val bindValueMethod = Holder.Reference::class.java.getDeclaredMethod("bindValue", Any::class.java).apply { isAccessible = true }
        bindValueMethod.invoke(holder, biome)

        val bindTagsMethod = Holder.Reference::class.java.getDeclaredMethod("bindTags", Collection::class.java).apply { isAccessible = true }
        bindTagsMethod.invoke(holder, emptySet<TagKey<Biome>>())

        frozenField.set(mappedRegistry, true)
    }
}