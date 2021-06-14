package world.cepi.region.event

import net.minestom.server.entity.Player
import net.minestom.server.event.trait.CancellableEvent
import net.minestom.server.event.trait.PlayerEvent
import net.minestom.server.utils.Position
import world.cepi.region.api.Region

class PlayerRegionUpdateEvent(private val _player: Player, currentRegion: Region?, newRegion: Region?, currentPos: Position, newPos: Position) : PlayerEvent, CancellableEvent {
    private var _cancelled = false

    override fun getPlayer(): Player {
        return _player
    }

    override fun isCancelled(): Boolean {
        return _cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        _cancelled = cancel
    }
}