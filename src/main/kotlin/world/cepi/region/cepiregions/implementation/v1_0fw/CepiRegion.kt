package world.cepi.region.cepiregions.implementation.v1_0fw

import net.minestom.server.data.Data
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.utils.BlockPosition
import world.cepi.region.Region
import world.cepi.region.RegionPool

class CepiRegion(override val name: String, override val pool: CepiRegionPool, val instance: Instance) : Region {

    val positions: List<Pair<BlockPosition, BlockPosition>> = listOf()

    override val instances = listOf(instance)

    override val defined = positions.isEmpty()

    // TODO
    override val volume = 0

    override fun isInside(pos: BlockPosition, world: Instance): Boolean {
        TODO("Not yet implemented")
    }

    override fun addBlocks(pos1: BlockPosition, pos2: BlockPosition, world: Instance): Int {
        TODO("Not yet implemented")
    }

    override fun removeBlocks(pos1: BlockPosition, pos2: BlockPosition, world: Instance): Int {
        TODO("Not yet implemented")
    }

    override fun iterateChunk(chunkX: Int, chunkZ: Int, world: Instance): Iterator<BlockPosition> {
        TODO("Not yet implemented")
    }

    override fun getPlayers(): MutableCollection<Player> {
        TODO("Not yet implemented")
    }

    override fun getEntities(): MutableCollection<Entity> {
        TODO("Not yet implemented")
    }

    override fun getEntities(vararg types: EntityType): MutableCollection<Entity> {
        TODO("Not yet implemented")
    }

}