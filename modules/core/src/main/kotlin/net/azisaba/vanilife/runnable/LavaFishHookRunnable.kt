package net.azisaba.vanilife.runnable

import org.bukkit.Fluid
import org.bukkit.Particle
import org.bukkit.entity.FishHook
import org.bukkit.entity.Snowball
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class LavaFishHookRunnable(private val fishHook: FishHook): BukkitRunnable() {
    private var bobbingVehicle: Snowball? = null

    override fun run() {
        if (fishHook.isDead || fishHook.isOnGround || fishHook.state != FishHook.HookState.UNHOOKED) {
            bobbingVehicle?.remove()
            cancel()
            return
        }

        val fishHookLocation = fishHook.location
        val fluidData = fishHookLocation.world.getFluidData(fishHookLocation)

        if (fluidData.fluidType != Fluid.LAVA) {
            bobbingVehicle?.apply { velocity = velocity.zero() }?.let {
                if (Random.nextDouble() < 0.05) {
                    fishHookLocation.world.spawnParticle(Particle.LAVA, fishHookLocation, 1, 0.2, 0.2, 0.2)
                }
            }
            return
        }

        val bobbingVehicle = bobbingVehicle ?: run {
            fishHookLocation.world.spawn(fishHookLocation.add(0.0, -0.25, 0.0), Snowball::class.java).apply {
                isInvisible = false
                isInvulnerable = true
                isPersistent = false
                isSilent = true
                setGravity(false)
                setNoPhysics(true)
                addPassenger(fishHook)
            }.also { this.bobbingVehicle = it }
        }

        bobbingVehicle.velocity = bobbingVehicle.velocity.apply {
            y += 0.1
            multiply(0.2)
        }
    }
}