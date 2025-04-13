package com.tksimeji.gonunne.sound

import org.bukkit.Sound

interface Music {
    val sound: Sound

    val minDelay: Int

    val maxDelay: Int

    val replaceCurrentMusic: Boolean
}