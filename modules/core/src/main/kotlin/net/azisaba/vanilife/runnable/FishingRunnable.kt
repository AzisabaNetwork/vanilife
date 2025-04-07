package net.azisaba.vanilife.runnable

import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.fish.Fish
import net.azisaba.vanilife.registry.Fishes
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

    var fish: Fish? = null

    var bobbingVehicle: Snowball? = null

    private var tickNumber = 0

    private var waitTime = Random.nextInt(hook.minWaitTime, hook.maxWaitTime)

    private val fishingRod = player.equipment.itemInMainHand

    private val noiseGenerator = SimplexNoiseGenerator(hook.world.seed * System.currentTimeMillis())

    init {
        instances.add(this)
        waitTime -= fishingRod.getEnchantmentLevel(Enchantment.LURE) * 5
    }

    override fun run() {
        if (hook.isDead) {
            cancel()
            return
        }

        hook.resetFishingState()

        updateWaitTime()
        updateBobbingVehicle()

        fish?.tick(player, hook, tickNumber, noiseGenerator)
        tickNumber++

        if (waitTime < 0 && fish == null) {
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
        val totalWeight = Fishes.sumOf { it.lootWeight }
        val random = Random.nextInt(totalWeight)
        var cumulativeWeight = 0

        for (fish in Fishes) {
            cumulativeWeight += fish.lootWeight
            if (random < cumulativeWeight) {
                this.fish = fish
                return
            }
        }
    }

    internal fun caught() {
        val fish = fish ?: throw IllegalStateException("No fish found")

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
        if (fish != null || (hook.state != FishHook.HookState.BOBBING && bobbingVehicle == null)) {
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