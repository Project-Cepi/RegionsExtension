package world.cepi.region.event;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.utils.Position;
import world.cepi.region.Region;

/**
 * Represents an event that is called when player attempts to leave a {@link Region}.
 */
public class PlayerLeaveRegionEvent extends PlayerMoveEvent implements RegionEvent {

    private final Region region;

    public PlayerLeaveRegionEvent(Player player, Region region, Position positionOutsideRegion) {
        super(player, positionOutsideRegion);

        this.region = region;
    }

    /**
     * @return The region that is being left.
     */
    @Override
    public Region getRegion() {
        return region;
    }
}
