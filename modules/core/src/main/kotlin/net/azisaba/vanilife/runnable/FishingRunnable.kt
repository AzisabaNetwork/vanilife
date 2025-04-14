package net.azisaba.vanilife.runnable

import com.tksimeji.gonunne.fishing.FishingEntry
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.event.FishHookLandEvent
import net.azisaba.vanilife.registry.FishingEntries
import org.bukkit.Fluid
import org.bukkit.block.BlockFace
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.*
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import org.bukkit.util.noise.SimplexNoiseGenerator
import kotlin.math.sqrt
import kotlin.random.Random

class FishingRunnable(private val player: Player, private val hook: FishHook): BukkitRunnable() {
    companion object {
        const val BOBBING_VEHICLE_METADATA = "hook_bobbing_vehicle"

        private val instances = mutableSetOf<FishingRunnable>()

        fun FishHook.runnable(): FishingRunnable = instances.first { it.hook == this }
    }

    val entity: Entity
        get() = bobbingVehicle ?: hook

    var fishingEntry: FishingEntry? = null

    var bobbingVehicle: Snowball? = null

    private var tickNumber = 0

    private var waitTime = Random.nextInt(hook.minWaitTime, hook.maxWaitTime)

    private val fishingRod = player.equipment.itemInMainHand

    private val noiseGenerator = SimplexNoiseGenerator(hook.world.seed * System.currentTimeMillis())

    private var eventCalled = false

    init {
        instances.add(this)
        waitTime -= fishingRod.getEnchantmentLevel(Enchantment.LURE) * 5
    }

    override fun run() {
        if (hook.isDead) {
            cancel()
            return
        }

        if (!eventCalled && hook.state == FishHook.HookState.BOBBING) {
            val fluidData = hook.world.getFluidData(hook.location)
            FishHookLandEvent(hook, player, fluidData, fishingRod)
            eventCalled = true
        }

        hook.resetFishingState()

        updateWaitTime()
        updateBobbingVehicle()

        fishingEntry?.tick(player, hook, tickNumber, noiseGenerator)
        tickNumber++

        if (waitTime < 0 && fishingEntry == null) {
            bite()
        }
    }

    override fun cancel() {
        instances.remove(this)
        hook.remove()
        bobbingVehicle?.remove()
        super.cancel()
    }

    private fun bite() {
        this.fishingEntry = FishingEntries.random()
    }

    internal fun caught() {
        val fish = fishingEntry ?: throw IllegalStateException("No fish found")

        for (itemStack in fish.loot(player, hook)) {
            val itemEntity = hook.world.createEntity(hook.location, Item::class.java).apply {
                val dx = player.x - hook.x
                val dy = player.y - hook.y
                val dz = player.z - hook.z
                val velocity = Vector(dx * 0.1, dy * 0.1 + sqrt(sqrt(dx * dx + dy * dy + dz * dz)) * 0.08, dz * 0.1)

                this.velocity = velocity
                this.itemStack = itemStack
            }
            itemEntity.spawnAt(hook.location)
        }

        fishingRod.damage(1, player)
        cancel()
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

            if (!eventCalled) {
                FishHookLandEvent(hook, player, fluidData, fishingRod).callEvent()
                eventCalled = true
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

    private fun updateWaitTime() {
        if (fishingEntry != null || (hook.state != FishHook.HookState.BOBBING && bobbingVehicle == null)) {
            return
        }

        if (hook.isSkyInfluenced && hook.location.block.getRelative(BlockFace.UP).lightFromSky <= 0 && Random.nextFloat() < 0.5) {
            return
        }

        if (hook.isRainInfluenced && hook.isInRain && Random.nextFloat() < 0.25) {
            waitTime--
        }

        waitTime--
    }
}