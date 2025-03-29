package net.azisaba.vanilife.world

import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*
import kotlin.math.*

class CavernChunkGenerator: ChunkGenerator() {
    companion object {
        private const val REGION_SIZE_CHUNKS = 32
        private const val REGION_SIZE_BLOCKS = REGION_SIZE_CHUNKS * 16

        private const val CLUSTER_GENERATION_PROBABILITY = 0.8
        private const val CELLS_PER_CLUSTER = 56
        private const val CELL_SPREAD_DISTANCE = 69.0
        private const val CELL_VERTICAL_VARIANCE = 10.0

        private const val CELL_BASE_RADIUS = 12.0
        private const val CELL_RADIUS_VARIATION = 5.0

        private const val VERTICAL_EXPANSION_FACTOR = 1.2
        private const val BOTTOM_PADDING = 3
        private const val CEILING_NOISE_AMPLITUDE = 6.0
        private const val NOISE_FREQUENCY = 0.3

        private const val Y_BASE = 20
        private const val Y_VARIANCE = 5

        private const val WALL_EPSILON = 3
        private const val ORE_GENERATION_RATE = 0.0005
        private const val CLUMP_PROPAGATION_RATE = 0.4
    }

    private val chunkCells = mutableListOf<Cell>()

    override fun generateCaves(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        val chunkMinX = chunkX * 16
        val chunkMaxX = chunkMinX + 16
        val chunkMinZ = chunkZ * 16
        val chunkMazZ = chunkMinZ + 16

        val regionMinX = Math.floorDiv(chunkMinX, REGION_SIZE_BLOCKS) - 1
        val regionMaxX = Math.floorDiv(chunkMaxX - 1, REGION_SIZE_BLOCKS) + 1
        val regionMinZ = Math.floorDiv(chunkMinZ, REGION_SIZE_BLOCKS) - 1
        val regionMaxZ = Math.floorDiv(chunkMazZ - 1, REGION_SIZE_BLOCKS) + 1

        for (regionX in regionMinX..regionMaxX) {
            for (regionZ in regionMinZ..regionMaxZ) {
                val regionSeed = worldInfo.seed xor ((regionX.toLong() shl 32)) or (regionZ.toLong() and 0xffffffffL)
                val regionRandom = Random(regionSeed)

                if (regionRandom.nextDouble() >= CLUSTER_GENERATION_PROBABILITY) {
                    continue
                }

                val clusterCenterX = regionX * REGION_SIZE_BLOCKS + regionRandom.nextInt(REGION_SIZE_BLOCKS)
                val clusterCenterY = Y_BASE + regionRandom.nextInt(Y_VARIANCE)
                val clusterCenterZ = regionZ * REGION_SIZE_BLOCKS + regionRandom.nextInt(REGION_SIZE_BLOCKS)

                for (i in 0 until CELLS_PER_CLUSTER) {
                    val offsetX = regionRandom.nextDouble() * 2 * CELL_SPREAD_DISTANCE - CELL_SPREAD_DISTANCE
                    val offsetZ = regionRandom.nextDouble() * 2 * CELL_SPREAD_DISTANCE - CELL_SPREAD_DISTANCE
                    val offsetY = regionRandom.nextDouble() * CELL_VERTICAL_VARIANCE - (CELL_VERTICAL_VARIANCE / 2)

                    val cellCenterX = clusterCenterX + offsetX
                    val cellCenterY = clusterCenterY + offsetY
                    val cellCenterZ = clusterCenterZ + offsetZ

                    val cellRadius = CELL_BASE_RADIUS + regionRandom.nextDouble() * CELL_RADIUS_VARIATION

                    if (isSphereIntersectingChunk(
                            cellCenterX, cellCenterY, cellCenterZ, cellRadius,
                            chunkMinX.toDouble(), chunkMaxX.toDouble(),
                            chunkMinZ.toDouble(), chunkMazZ.toDouble(),
                            worldInfo.minHeight.toDouble(), worldInfo.maxHeight.toDouble()
                        )) {
                        chunkCells.add(Cell(cellCenterX, cellCenterY, cellCenterZ, cellRadius))
                        generateCell(chunkData, worldInfo, chunkX, chunkZ, cellCenterX, cellCenterY, cellCenterZ, cellRadius)
                    }
                }
            }
        }

        generateOres(chunkData, worldInfo, chunkX, chunkZ, random)
    }

    private fun generateCell(chunkData: ChunkData, worldInfo: WorldInfo, chunkX: Int, chunkZ: Int, centerX: Double, centerY: Double, centerZ: Double, radius: Double) {
        fun ceilingNoise(x: Double, z: Double): Double {
            return (sin(x + z) + cos(0.5 * x - 0.7 * z)) / 2.0
        }

        val chunkWorldX = chunkX * 16
        val chunkWorldZ = chunkZ * 16

        val startX = max(chunkWorldX, floor(centerX - radius).toInt())
        val endX = min(chunkWorldX + 16, ceil(centerX + radius).toInt())
        val startY = max(0, floor(centerY - radius * VERTICAL_EXPANSION_FACTOR).toInt())
        val endY = min(worldInfo.maxHeight, ceil(centerY + radius * VERTICAL_EXPANSION_FACTOR).toInt())
        val startZ = max(chunkWorldZ, floor(centerZ - radius).toInt())
        val endZ = min(chunkWorldZ + 16, ceil(centerZ + radius).toInt())

        val flatBottomThreshold = centerY - radius * VERTICAL_EXPANSION_FACTOR + BOTTOM_PADDING

        for (x in startX until endX) {
            for (y in startY until  endY) {
                for (z in startZ until endZ) {
                    val dx = x + 0.5 - centerX
                    val dz = z + 0.5 - centerZ
                    val rawDy = y + 0.5 - centerY
                    val scaledDy = rawDy / VERTICAL_EXPANSION_FACTOR

                    if (y + 0.5 < flatBottomThreshold) {
                        continue
                    }

                    val effectiveRadius = if (y + 0.5 <= centerY) {
                        radius
                    } else {
                        val noiseVal = max(0.0, ceilingNoise((x + 0.5) * NOISE_FREQUENCY, (z + 0.5) * NOISE_FREQUENCY))
                        max(0.0, radius - noiseVal * CEILING_NOISE_AMPLITUDE)
                    }

                    if (dx * dx + scaledDy * scaledDy + dz * dz <= effectiveRadius * effectiveRadius) {
                        val localX = x - chunkWorldX
                        val localZ = z - chunkWorldZ
                        chunkData.setBlock(localX, y, localZ, Material.AIR)
                    }
                }
            }
        }
    }

    private fun generateOres(chunkData: ChunkData, worldInfo: WorldInfo, chunkX: Int, chunkZ: Int, random: Random) {
        val cells = chunkCells.toList()
        val chunkWorldX = chunkX * 16
        val chunkWorldZ = chunkZ * 16
        for (x in chunkWorldX until chunkWorldX + 16) {
            for (y in max(0, worldInfo.minHeight) until worldInfo.maxHeight) {
                for (z in chunkWorldZ until chunkWorldZ + 16) {
                    val localX = x - chunkWorldX
                    val localZ = z - chunkWorldZ

                    if (chunkData.getBlockData(localX, y, localZ).material == Material.AIR) {
                        continue
                    }

                    val distance = cells.fold(Double.MAX_VALUE) { acc, cell ->
                        val dx = x + 0.5 - cell.centerX
                        val dy = (y + 0.5 - cell.centerY) / VERTICAL_EXPANSION_FACTOR
                        val dz = z + 0.5 - cell.centerZ
                        val cellDistance = sqrt(dx * dx + dy * dy + dz * dz) - cell.radius
                        min(acc, cellDistance)
                    }

                    if (abs(distance) > WALL_EPSILON || random.nextDouble() > ORE_GENERATION_RATE) {
                        continue
                    }

                    chunkData.setBlock(localX, y, localZ, Material.DIAMOND_ORE)

                    for (dx in -1..1) {
                        for (dy in -1..1) {
                            for (dz in -1..1) {
                                if (dx == 0 && dy == 0 && dz == 0) {
                                    continue
                                }

                                val nx = x + dx
                                val ny = y + dy
                                val nz = z + dz

                                if (nx in chunkWorldX until (chunkWorldX + 16) &&
                                    nz in chunkWorldZ until (chunkWorldZ + 16) &&
                                    ny in worldInfo.minHeight until worldInfo.maxHeight) {
                                    val nLocalX = nx - chunkWorldX
                                    val nLocalZ = nz - chunkWorldZ

                                    if (chunkData.getBlockData(nLocalX, ny, nLocalZ).material != Material.AIR &&
                                        random.nextDouble() < CLUMP_PROPAGATION_RATE) {
                                        chunkData.setBlock(nLocalX, ny, nLocalZ, Material.DIAMOND_ORE)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun shouldGenerateDecorations(): Boolean = true

    override fun shouldGenerateMobs(): Boolean = true

    override fun shouldGenerateNoise(): Boolean = true

    override fun shouldGenerateStructures(): Boolean = true

    override fun shouldGenerateSurface(): Boolean = true

    private fun isSphereIntersectingChunk(cx: Double, cy: Double, cz: Double, radius: Double, chunkMinX: Double, chunkMaxX: Double, chunkMinZ: Double, chunkMaxZ: Double, minY: Double, maxY: Double): Boolean {
        return (cx + radius >= chunkMinX && cx - radius < chunkMaxX &&
                cz + radius >= chunkMinZ && cz - radius < chunkMaxZ &&
                cy + radius >= minY && cy - radius < maxY)
    }

    private data class Cell(val centerX: Double, val centerY: Double, val centerZ: Double, val radius: Double)
}