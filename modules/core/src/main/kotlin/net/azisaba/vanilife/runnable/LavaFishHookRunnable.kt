package net.azisaba.vanilife.runnable

import org.bukkit.Fluid
import org.bukkit.Particle
import org.bukkit.entity.FishHook
import org.bukkit.entity.Snowball
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class LavaFishHookRunnable(val fishHook: FishHook): BukkitRunnable() {
    companion object {
        internal val instances
            get() = _instances.toSet()

        private val _instances = mutableSetOf<LavaFishHookRunnable>()
    }

    val bobbingVehicle
        get() = _bobbingVehicle

    private var _bobbingVehicle: Snowball? = null

    init {
        _instances.add(this)
    }

    override fun run() {
        if (fishHook.isDead || fishHook.state == FishHook.HookState.BOBBING) {
            _bobbingVehicle?.remove()
            cancel()
            return
        }

        val fishHookLocation = fishHook.location
        val fluidData = fishHookLocation.world.getFluidData(fishHookLocation)

        if (fluidData.fluidType != Fluid.LAVA) {
            _bobbingVehicle?.apply {
                if (velocity.y != 0.0) {
                    velocity = velocity.apply { y = 0.0 }
                }

                if (Random.nextDouble() < 0.05) {
                    fishHookLocation.world.spawnParticle(Particle.LAVA, fishHookLocation, 1, 0.2, 0.2, 0.2)
                }
            }
            return
        }

        if (_bobbingVehicle?.isDead == true) {
            _bobbingVehicle = null
        }

        val bobbingVehicle = _bobbingVehicle ?: run {
            fishHookLocation.world.spawn(fishHookLocation.add(0.0, -0.25, 0.0), Snowball::class.java).apply {
                isInvisible = true
                isInvulnerable = true
                isPersistent = true
                isSilent = true
                setGravity(false)
                setNoPhysics(true)
                addPassenger(fishHook)
            }.also { _bobbingVehicle = it }
        }

        bobbingVehicle.velocity = bobbingVehicle.velocity.apply { y = 0.1 }
    }

    override fun cancel() {
        _instances.remove(this)
        super.cancel()
    }
}