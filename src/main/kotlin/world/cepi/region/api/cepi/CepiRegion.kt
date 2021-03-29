package world.cepi.region.api.cepi

import net.minestom.server.data.Data
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition
import world.cepi.region.Selection
import world.cepi.region.api.Region

data class CepiRegion(override val name: String, override val pool: CepiRegionPool, val instance: Instance) : Region {

    private var _data: Data? = null

    val selections: List<Selection> = listOf()

    override val instances = listOf(instance)

    override val defined
        get() = selections.isEmpty()

    // TODO
    override val volume = 0

    override fun isInside(pos: BlockPosition, world: Instance) =
        selections.any { it.isInside(instance, pos) }

    override fun addBlocks(pos1: BlockPosition, pos2: BlockPosition, instance: Instance): Int {
        TODO("Not yet implemented")
    }

    override fun removeBlocks(pos1: BlockPosition, pos2: BlockPosition, instance: Instance): Int {
        TODO("Not yet implemented")
    }

    override fun iterateChunk(chunkX: Int, chunkZ: Int, world: Instance): Iterator<BlockPosition> {
        TODO("Not yet implemented")
    }

    override val entities: Collection<Entity> =
        selections.map { it.findInside(instance) }.flatten()

    override val players: Collection<Player> =
        entities.filterIsInstance<Player>()

    override fun entities(vararg types: EntityType): Collection<Entity> =
        entities.filter { types.contains(it.entityType) }

    override fun getData() = _data

    override fun setData(data: Data?) {
        _data = data
    }

}