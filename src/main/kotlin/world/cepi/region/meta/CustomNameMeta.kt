package world.cepi.region.meta

import world.cepi.kstom.adventure.asMini
import world.cepi.region.api.Region

class CustomNameMeta(val name: String) : RegionMeta() {

    override fun apply(region: Region) {
        region.displayName = name.asMini()
    }

    companion object : RegionMetaCompanion {
        override fun unapply(region: Region) {
            region.displayName = null
        }
    }

}