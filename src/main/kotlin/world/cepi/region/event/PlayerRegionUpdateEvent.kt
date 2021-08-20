package world.cepi.region.event

import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Player
import net.minestom.server.event.trait.CancellableEvent
import net.minestom.server.event.trait.PlayerEvent
import world.cepi.region.api.Region

class PlayerRegionUpdateEvent(
    private val _player: Player,
    val currentRegion: Region?,
    val newRegion: Region?,
    val currentPos: Pos,
    val newPos: Pos
) : PlayerEvent, CancellableEvent {
    private var _cancelled = false

    override fun getPlayer() =
        _player

    override fun isCancelled() =
        _cancelled


    override fun setCancelled(cancel: Boolean) {
        _cancelled = cancel
    }
}