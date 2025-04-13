package net.azisaba.vanilife.registry

import com.tksimeji.gonunne.registry.impl.RegistryImpl
import net.kyori.adventure.util.UTF8ResourceBundleControl
import java.util.Locale
import java.util.ResourceBundle

object LanguageBundles: RegistryImpl<Locale, ResourceBundle>() {
    val ENGLISH = register(Locale.US, ResourceBundle.getBundle("lang.Bundle", Locale.US, UTF8ResourceBundleControl.get()))

    val JAPANESE = register(Locale.JAPAN, ResourceBundle.getBundle("lang.Bundle", Locale.JAPAN, UTF8ResourceBundleControl.get()))
}