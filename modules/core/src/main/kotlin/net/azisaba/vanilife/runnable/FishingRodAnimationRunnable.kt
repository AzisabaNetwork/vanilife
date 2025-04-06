package net.azisaba.vanilife.runnable

import com.github.retrooper.packetevents.util.Vector3d
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityRelativeMoveAndRotation
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity
import net.azisaba.vanilife.extension.sendPacketSilently
import net.azisaba.vanilife.extension.toPacketEventsEntityType
import net.azisaba.vanilife.extension.toPacketEventsLocation
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class FishingRodAnimationRunnable(val player: Player): BukkitRunnable() {
    companion object {
        private const val POINTS = 64

        private const val RADIUS = 3.0

        private const val CHASING_GAP = POINTS / 2

        private const val STEP = 2 * PI / POINTS
        private const val STEP_INCREMENT = 2

        private const val FISH_ID = 998
        private const val CAT_ID = 999

        internal val instances = mutableSetOf<FishingRodAnimationRunnable>()

        internal fun isAnimationNeeded(player: Player): Boolean {
            return player.equipment.itemInMainHand.type == Material.FISHING_ROD && player.fishHook == null && !player.isInWater && player.isOnline
        }
    }

    private var fishIndex = 0

    private var spawnPackets = 0

    private val entityLocationCache = mutableMapOf<Int, Location>()

    init {
        if (instances.any { it.player == player }) {
            throw IllegalStateException()
        }
        instances.add(this)
    }

    override fun run() {
        if (!isAnimationNeeded(player)) {
            cancel()
            return
        }

        for (i in 0 until POINTS) {
            val angle = i * STEP
            val x = RADIUS * cos(angle)
            val z = RADIUS * sin(angle)
            val particleLocation = player.location.clone().add(x, 0.5, z)
            player.spawnParticle(Particle.DUST, particleLocation, 1, Particle.DustOptions(Color.AQUA, 0.5F))
        }

        fishIndex = (fishIndex + STEP_INCREMENT) % POINTS

        val angleFish = 2 * PI * fishIndex / POINTS

        val catIndex = (fishIndex - CHASING_GAP + POINTS) % POINTS
        val angleCat = 2 * PI * catIndex / POINTS

        val fishPos = computeLocation(player.location, angleFish, RADIUS)
        val catPos = computeLocation(player.location, angleCat, RADIUS)
        sendEntityPacket(FISH_ID, EntityType.COD, fishPos)
        sendEntityPacket(CAT_ID, EntityType.CAT, catPos)
    }

    override fun cancel() {
        val packet = WrapperPlayServerDestroyEntities(FISH_ID, CAT_ID)
        player.sendPacketSilently(packet)
        instances.remove(this)
        super.cancel()
    }

    private fun sendEntityPacket(entityId: Int, entityType: EntityType, location: Location) {
        if (spawnPackets < 2) {
            val spawnPacket = WrapperPlayServerSpawnEntity(entityId, UUID.randomUUID(), entityType.toPacketEventsEntityType(), location.toPacketEventsLocation(), 0F, 0, Vector3d())
            player.sendPacketSilently(spawnPacket)
            spawnPackets++
        }

        val oldLocation = entityLocationCache[entityId] ?: location
        val deltaX = location.x - oldLocation.x
        val deltaY = location.y - oldLocation.y
        val deltaZ = location.z - oldLocation.z

        val packet = WrapperPlayServerEntityRelativeMoveAndRotation(entityId, deltaX, deltaY, deltaZ, location.yaw, location.pitch, true)

        player.sendPacketSilently(packet)
        entityLocationCache[entityId] = location
    }

    private fun computeLocation(center: Location, angle: Double, radius: Double): Location {
        val x = center.x + radius * cos(angle)
        val z = center.z + radius * sin(angle)
        return Location(center.world, x, center.y + 0.25, z, computeYaw(angle), 0F)
    }

    private fun computeYaw(angle: Double): Float {
        var yaw = Math.toDegrees(angle).toFloat()
        while (yaw > 180F) yaw -= 360F
        while (yaw < -180F) yaw += 360F
        return yaw
    }
}