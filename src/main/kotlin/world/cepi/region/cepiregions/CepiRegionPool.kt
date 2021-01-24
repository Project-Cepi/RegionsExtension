package world.cepi.region.cepiregions

import world.cepi.region.Region
import world.cepi.region.RegionPool

class CepiRegionPool(
    private val name: String
): RegionPool {

    private val regions: MutableMap<String, Region> = mutableMapOf()

    override fun getName() = name

    override fun getRegions() = regions.values

    override fun contains(region: Region) = regions.contains(region.getName())

    override fun size() = regions.size

    override fun getRegion(name: String) = regions[name]

    override fun createRegion(name: String): Region {
        val region = CepiRegion(name, this)
        regions[name] = region
        return region
    }

    override fun remove(region: Region) {
        regions.remove(region.getName())
    }
}