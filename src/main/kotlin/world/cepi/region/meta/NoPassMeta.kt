package world.cepi.region.meta

import world.cepi.region.api.Region

class NoPassMeta : RegionMeta() {

    override fun apply(region: Region) {
        Region.noPassRegions += region
    }

    companion object : RegionMetaCompanion {
        override fun unapply(region: Region) {
            Region.noPassRegions -= region
        }
    }

}