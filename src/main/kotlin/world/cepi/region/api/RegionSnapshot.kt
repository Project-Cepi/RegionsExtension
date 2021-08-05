package world.cepi.region.api

import kotlinx.serialization.Serializable
import java.time.LocalTime

@Serializable
class RegionSnapshot(val region: Region) {
    val time: Long = System.currentTimeMillis() / 1000

    val blockStates: Set<Short>
    get() {
        val states = mutableSetOf<Short>()
        region.selections.forEach {
            for (x in it.xRange) {
                for (y in it.yRange) {
                    for (z in it.zRange) {
                        states.add(region.instance.getBlockStateId(x, y, z))
                    }
                }
            }
        }

        return states.toSet()
    }

    var blockStatesCache: Set<Short> = blockStates
        private set

    fun updateCache() { this.blockStatesCache = blockStates }
}