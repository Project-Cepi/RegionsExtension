package world.cepi.region.meta

import world.cepi.region.api.Region

interface RegionMetaCompanion {

    fun unapply(region: Region)

}