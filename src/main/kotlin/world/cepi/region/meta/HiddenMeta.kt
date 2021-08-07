package world.cepi.region.meta

import world.cepi.region.api.Region

class HiddenMeta : RegionMeta() {

    override fun apply(region: Region) {
        region.hidden = true
    }

    companion object : RegionMetaCompanion {
        override fun unapply(region: Region) {
            region.hidden = false
        }
    }

}