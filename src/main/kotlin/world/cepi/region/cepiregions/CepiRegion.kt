package world.cepi.region.cepiregions

import net.minestom.server.data.Data
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition
import world.cepi.region.Region

class CepiRegion(private val name: String, private val pool: CepiRegionPool): Region {

    val blocks = mutableListOf<BlockPosition>()
    private var data: Data? = null

    override fun getName() = name

    override fun getPool() = pool

    override fun getWorlds(): Collection<Instance> {
        TODO("Not yet implemented")
    }

    override fun isDefined(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getVolume() = blocks.size

    override fun addBlocks(pos1: BlockPosition, pos2: BlockPosition, world: Instance): Int {
        TODO("Not yet implemented")
    }

    override fun removeBlocks(pos1: BlockPosition, pos2: BlockPosition, world: Instance): Int {
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

    override fun getData() = data

    override fun setData(data: Data?) {
        this.data = data
    }
}