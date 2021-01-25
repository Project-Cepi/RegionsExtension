package world.cepi.region.event;

import world.cepi.region.Region;

/**
 * Represents a [Region] related event.
 *
 * @since RegionAPI 1.0
 */
interface RegionEvent {

    /**
     * @return The [Region] in question.
     *
     * @since RegionAPI 1.0
     */
    fun getRegion(): Region

}
