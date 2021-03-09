package world.cepi.region.cepiregions.implementation.v1_0fw

import world.cepi.region.RegionPool
import world.cepi.region.RegionProvider
import java.util.*

class CepiRegionProvider : RegionProvider {

    val pools = ArrayList<CepiRegionPool>()

    override fun getVersion(): String {
        return "1.0"
    }

    override fun getImplementationName(): String {
        return "CepiRegions"
    }

    override fun getPools(): Collection<CepiRegionPool> {
        return pools
    }

    override fun getPool(name: String): CepiRegionPool? {
        for (pool in pools) {
            if (pool.getName() == name) return pool
        }
        return null
    }

    override fun createPool(name: String): CepiRegionPool {
        TODO("Not yet implemented")
    }

    override fun removePool(pool: RegionPool) {
        if (!getPools().contains(pool))
            throw IllegalStateException("region pool ${pool.getName()} is not registered in ${getImplementationName()}")
        // TODO: Remove
    }

}