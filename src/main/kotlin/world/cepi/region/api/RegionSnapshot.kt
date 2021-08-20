package world.cepi.region.api

import kotlinx.serialization.Serializable
import world.cepi.region.size
import java.time.LocalTime

@Serializable
class RegionSnapshot(val blockStates: List<Short>) {
    val time: Long = System.currentTimeMillis()

    companion object {
        fun from(region: Region) = region.selections.map { selection ->
            selection.xRange.map { x ->
                selection.yRange.map { y ->
                    selection.zRange.map { z ->
                        region.instance.getBlock(x, y, z).id()
                    }
                }.flatten()
            }.flatten()
        }.flatten()
    }

}