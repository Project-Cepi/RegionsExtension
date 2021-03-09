package world.cepi.region.event

import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerMoveEvent
import net.minestom.server.utils.Position
import world.cepi.region.Region

/**
 * Represents an event that is called when player attempts to enter a [Region].
 *
 * @since RegionAPI 1.0
 */
class PlayerEnterRegionEvent(player: Player, override val region: Region, positionInsideRegion: Position) : PlayerMoveEvent(player, positionInsideRegion), RegionEvent
