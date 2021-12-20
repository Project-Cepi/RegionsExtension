package world.cepi.region.event

import net.minestom.server.event.player.PlayerMoveEvent
import net.minestom.server.tag.Tag
import world.cepi.kstom.Manager
import world.cepi.region.api.Region
import world.cepi.region.api.RegionProvider
import world.cepi.region.api.region

object PlayerRegionHandler {

    fun register(event: PlayerMoveEvent) = with(event) {

        val oldRegion = player.region

        val newRegion = RegionProvider.regions.values
            .filter { it.instance.uniqueId == player.instance?.uniqueId }
            .filter { it.contains(newPosition) }
            .minByOrNull(Region::volume)

        if (oldRegion == newRegion)
            return@with

        val regionEvent = PlayerRegionUpdateEvent(player, oldRegion, newRegion, player.position)
        Manager.globalEvent.call(regionEvent)

        if (!regionEvent.isCancelled) {
            player.region = newRegion
        }

        event.isCancelled = regionEvent.isCancelled
    }
}