package world.cepi.region.event;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.utils.Position;
import world.cepi.region.Region;

/**
 * Represents an event that is called when player attempts to enter a {@link Region}.
 */
public class PlayerEnterRegionEvent extends PlayerMoveEvent implements RegionEvent {

    private final Region region;

    public PlayerEnterRegionEvent(Player player, Region region, Position positionInsideRegion) {
        super(player, positionInsideRegion);

        this.region = region;
    }

    /**
     * @return The region that is being entered.
     */
    @Override
    public Region getRegion() {
        return region;
    }
}
