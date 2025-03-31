package net.azisaba.vanilife.font

import net.azisaba.vanilife.registry.IRegistry
import net.azisaba.vanilife.registry.Keyed

interface Font: IRegistry<String, Char>, Keyed {
}