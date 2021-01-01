package world.cepi.region.cepiregions

import world.cepi.region.RegionPool
import world.cepi.region.RegionProvider

class CepiRegionProvider : RegionProvider {

    override fun getVersion(): String {
        return "1.0"
    }

    override fun getImplementationName(): String {
        return "CepiRegions"
    }

    override fun getPools(): Collection<RegionPool> {
        TODO("Not yet implemented")
    }

    override fun getPool(name: String): RegionPool? {
        TODO("Not yet implemented")
    }

    override fun createPool(name: String): RegionPool {
        TODO("Not yet implemented")
    }

    override fun removePool(pool: RegionPool) {
        TODO("Not yet implemented")
    }

}