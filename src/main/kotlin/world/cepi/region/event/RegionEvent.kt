package world.cepi.region.event;

import world.cepi.region.Region;

/**
 * Represents a [Region] related event.
 */
interface RegionEvent {

    /**
     * @return The [Region] in question.
     */
    fun getRegion(): Region

}
