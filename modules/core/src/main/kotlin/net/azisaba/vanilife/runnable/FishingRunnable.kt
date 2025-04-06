package net.azisaba.vanilife.runnable

import net.azisaba.vanilife.Vanilife
import org.bukkit.Fluid
import org.bukkit.entity.Entity
import org.bukkit.entity.FishHook
import org.bukkit.entity.Snowball
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.scheduler.BukkitRunnable

class FishingRunnable(private val hook: FishHook): BukkitRunnable() {
    companion object {
        const val BOBBING_VEHICLE_METADATA = "hook_bobbing_vehicle"

        private val instances = mutableSetOf<FishingRunnable>()

        fun FishHook.runnable(): FishingRunnable = instances.first { it.hook == this }
    }

    val entity: Entity
        get() = bobbingVehicle ?: hook

    var bobbingVehicle: Snowball? = null

    init {
        instances.add(this)
    }

    override fun run() {
        if (hook.isDead) {
            cancel()
            bobbingVehicle?.remove()
            return
        }

        updateBobbingVehicle()
    }

    override fun cancel() {
        instances.remove(this)
        super.cancel()
    }

    private fun updateBobbingVehicle() {
        if (hook.state == FishHook.HookState.BOBBING) {
            bobbingVehicle?.remove()
            return
        }

        val hookLocation = hook.location
        val fluidData = hookLocation.world.getFluidData(hookLocation)

        if (fluidData.fluidType != Fluid.LAVA) {
            bobbingVehicle?.apply {
                if (velocity.y != 0.0) velocity = velocity.apply { y = 0.0 }
            }
        } else {
            if (bobbingVehicle?.isDead == true) {
                bobbingVehicle = null
            }

            val bobbingVehicle = bobbingVehicle ?: hookLocation.world.spawn(hookLocation.add(0.0, -0.25, 0.0), Snowball::class.java).apply {
                isInvisible = true
                isInvulnerable = true
                isPersistent = true
                isSilent = true
                setGravity(false)
                setMetadata(BOBBING_VEHICLE_METADATA, FixedMetadataValue(Vanilife.plugin, null))
                setNoPhysics(true)
                addPassenger(hook)
            }.also { bobbingVehicle = it }

            bobbingVehicle.velocity = bobbingVehicle.velocity.apply { y = 0.1 }
        }
    }

    /*
            val center = player.location
        val step = 2 * PI / POINTS

        for (i in 0 until POINTS) {
            val angle = i * step
            val x = RADIUS * cos(angle)
            val z = RADIUS * sin(angle)
            val particleLocation = center.clone().add(x, 0.0, z)
            player.spawnParticle(Particle.DUST, particleLocation, 1, Particle.DustOptions(Color.YELLOW, 0.5F))
        }
     */
}