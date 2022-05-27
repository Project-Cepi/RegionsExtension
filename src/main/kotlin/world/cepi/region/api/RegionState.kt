package world.cepi.region.api

import kotlinx.serialization.Serializable
import net.minestom.server.instance.block.Block
import world.cepi.kstom.serializer.BlockSerializer

@Serializable
data class RegionState(val blockStates: List<@Serializable(with = BlockSerializer::class) Block>) {
    companion object {
        fun from(region: Region) = RegionState(region.selections.map { selection ->
            selection.xRange.map { x ->
                selection.yRange.map { y ->
                    selection.zRange.map { z ->
                        region.instance.getBlock(x, y, z)
                    }
                }.flatten()
            }.flatten()
        }.flatten())
    }

    fun put(region: Region) {
        
    }
}