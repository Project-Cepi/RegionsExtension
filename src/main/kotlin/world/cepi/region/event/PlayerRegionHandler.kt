package world.cepi.region.event

import net.minestom.server.event.player.PlayerMoveEvent
import world.cepi.kstom.Manager
import world.cepi.region.api.Region
import world.cepi.region.api.RegionProvider

object PlayerRegionHandler {

    fun register(event: PlayerMoveEvent) = with(event) {
        var currentRegion: Region? = null
        var newRegion: Region? = null

        RegionProvider.regions.forEach { (_, region) ->
            if (region.isInside(player.position.toBlockPosition())) {
               currentRegion = region
            }
            if (region.isInside(newPosition.toBlockPosition())) {
                newRegion = region
            }
        }

        if (currentRegion == newRegion)
            return@with

        val regionEvent = PlayerRegionUpdateEvent(player, currentRegion, newRegion, player.position, newPosition)
        Manager.globalEvent.call(regionEvent)
        event.isCancelled = regionEvent.isCancelled
    }
}