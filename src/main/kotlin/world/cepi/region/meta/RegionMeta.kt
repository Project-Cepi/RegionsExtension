package world.cepi.region.meta

import world.cepi.region.api.Region

sealed class RegionMeta {

    abstract fun apply(region: Region)

}