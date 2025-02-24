package net.azisaba.vanilife.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.azisaba.vanilife.Vanilife
import net.azisaba.vanilife.data.Range
import net.azisaba.vanilife.extension.sendInfo
import net.azisaba.vanilife.extension.sendError
import net.azisaba.vanilife.util.runWithCooldown
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import kotlin.random.Random

object RandomTeleportCommand: CommandCreator {
    private val range: Range
        get() = Vanilife.pluginConfig.randomTeleport.range

    private val maxAttempts: Int
        get() = Vanilife.pluginConfig.randomTeleport.maxAttempts

    private val notSafeBlockTypes = setOf(
        Material.LAVA,
        Material.LAVA_CAULDRON,
        Material.WATER,
        Material.WATER_CAULDRON,
        Material.FIRE,
        Material.MAGMA_BLOCK
    )

    override fun create(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal("randomteleport")
            .requires { it.sender is Player }
            .executes(this::randomTeleportCommand)
    }

    private fun randomTeleportCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val player = ctx.source.sender as Player

        runWithCooldown(this, player) {
            player.sendInfo(Component.text("ランダムテレポートするね！これには少し時間がかかることがあるよ"))

            Bukkit.getScheduler().runTaskAsynchronously(Vanilife.plugin) { ->
                val world = player.world

                var location = getRandomLocation(world)

                var i = 0

                while (! isSafe(location)) {
                    location = getRandomLocation(world)

                    if (maxAttempts < i ++) {
                        player.sendError(Component.text("ごめんなさい！ランダムテレポートに失敗しちゃった！"))
                        return@runTaskAsynchronously
                    }
                }

                Bukkit.getScheduler().runTask(Vanilife.plugin) { ->
                    player.teleport(location)
                    player.playSound(player, Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 1.0f)
                }
            }
        }

        return Command.SINGLE_SUCCESS
    }

    private fun getRandomLocation(world: World): Location {
        return Location(world, Random.nextInt(range.min, range.max + 1).toDouble(), 0.0, Random.nextInt(range.min, range.max + 1).toDouble()).also {
            it.y = world.getHighestBlockYAt(it).toDouble() + 1
        }
    }

    private fun isSafe(location: Location): Boolean {
        val block = location.world.getBlockAt(location)
        val below = block.getRelative(BlockFace.DOWN)
        val above = block.getRelative(BlockFace.UP)

        return ! notSafeBlockTypes.contains(block.type) &&
                ! notSafeBlockTypes.contains(below.type) &&
                below.type.isSolid &&
                above.type == Material.AIR
    }
}