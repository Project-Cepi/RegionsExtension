package world.cepi.region.event;

import world.cepi.region.Region;

/**
 * Represents a {@link Region} related event.
 */
public interface RegionEvent {

    /**
     * @return The {@link Region} in question.
     */
    Region getRegion();

}
