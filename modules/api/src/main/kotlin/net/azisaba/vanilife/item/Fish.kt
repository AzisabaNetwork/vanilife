package net.azisaba.vanilife.item

interface Fish: Food {
    override val maxStackSize: Int
        get() = 16
}