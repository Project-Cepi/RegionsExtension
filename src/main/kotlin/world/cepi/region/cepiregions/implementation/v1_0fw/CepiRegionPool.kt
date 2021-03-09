package world.cepi.region.cepiregions.implementation.v1_0fw

import world.cepi.region.Region
import world.cepi.region.RegionPool
import java.util.*

class CepiRegionPool(override val name: String) : RegionPool {

    override val regions = ArrayList<CepiRegion>()

    override fun contains(region: Region) =
        regions.contains(region)

    override val size
        get() = regions.size

    override operator fun get(name: String): CepiRegion? {
        for (region in regions) {
            if (region.name == name) return region
        }
        return null
    }

    override fun createRegion(name: String): CepiRegion {
        TODO("Not yet implemented")
    }

    override fun remove(region: Region) {
        if (!contains(region))
            throw IllegalStateException("attempted to remove non-member region '${region.name}' from pool '$name'")

        regions.remove(region)
    }

}