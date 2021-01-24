package world.cepi.region.cepiregions

import world.cepi.region.RegionPool
import world.cepi.region.RegionProvider

class CepiRegionProvider : RegionProvider {

    private val pools: MutableMap<String, CepiRegionPool> = mutableMapOf()

    override fun getVersion(): String {
        return "1.0"
    }

    override fun getImplementationName(): String {
        return "CepiRegions"
    }

    override fun getPools() = pools.values

    override fun getPool(name: String) = pools[name]

    override fun createPool(name: String): CepiRegionPool {
        val pool = CepiRegionPool(name)
        pools[name] = pool
        return pool
    }

    override fun removePool(pool: RegionPool) {
        pools.remove(pool.getName())
    }

}