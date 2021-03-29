package world.cepi.region.cepiregions.implementation.v1_0fw

import world.cepi.region.RegionPool
import world.cepi.region.RegionProvider
import java.util.*

class CepiRegionProvider : RegionProvider {

    override val pools = ArrayList<CepiRegionPool>()

    override val version = "1.0"

    override val implementationName = "CepiRegions"

    override operator fun get(name: String): CepiRegionPool? {
        for (pool in pools) {
            if (pool.name == name) return pool
        }
        return null
    }

    override fun createPool(name: String): CepiRegionPool {
        val pool = CepiRegionPool(name)

        pools.add(pool)

        return pool
    }

    override fun removePool(pool: RegionPool) {
        if (!pools.contains(pool))
            throw IllegalStateException("region pool ${pool.name} is not registered in $implementationName")

        pools.remove(pool)

    }

}