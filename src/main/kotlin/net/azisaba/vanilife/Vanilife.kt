package net.azisaba.vanilife

import org.bukkit.plugin.java.JavaPlugin

class Vanilife : JavaPlugin() {
    companion object {
        private var instance: Vanilife? = null

        fun plugin(): Vanilife {
            return instance!!
        }
    }

    override fun onEnable() {
        instance = this
    }
}
