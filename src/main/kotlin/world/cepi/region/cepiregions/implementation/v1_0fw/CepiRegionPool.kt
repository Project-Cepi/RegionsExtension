package world.cepi.region.cepiregions.implementation.v1_0fw

import world.cepi.region.Region
import world.cepi.region.RegionPool
import java.util.*

class CepiRegionPool(private val name: String) : RegionPool {

    val regions = ArrayList<CepiRegion>()

    override fun getName(): String {
        return name
    }

    override fun getRegions(): Collection<CepiRegion> {
        return regions
    }

    override fun contains(region: Region): Boolean {
        return regions.contains(region)
    }

    override fun size(): Int {
        return regions.size
    }

    override fun getRegion(name: String): CepiRegion? {
        for (region in regions) {
            if (region.getName() == name) return region
        }
        return null
    }

    override fun createRegion(name: String): CepiRegion {
        TODO("Not yet implemented")
    }

    override fun remove(region: Region) {
        if (!contains(region))
            throw IllegalStateException("attempted to remove non-member region '${region.getName()}' from pool '$name'")
        // TODO: remove
    }

}