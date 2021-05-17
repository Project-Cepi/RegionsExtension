package world.cepi.region.api

import net.minestom.server.instance.Instance

/**
 * Represents a RegionAPI implementation.
 *
 * <p> Current version: 1.0
 *
 * @since RegionAPI 1.0
 */
object RegionProvider {

    val regions: Map<String, Region> = mutableMapOf()

    /**
     * Gets the [Region] with the specified name,
     * or null, if it doesn't exist.
     *
     * @param name The specified name
     *
     * @return The pool, with the given name, or null.
     */
    operator fun get(name: String): Region? {
        return regions[name]
    }

    /**
     * Creates a new [Region] in a given [Instance] and register it.
     *
     * The region created doesn't have any initial volume.
     *
     * @param name Unique name of the region
     * @param instance The instance that this region resides in
     *
     * @return The created region
     *
     * @throws IllegalStateException If the name provided already exists
     */
    fun createRegion(name: String, instance: Instance): Region {
        regions as MutableMap

        if(regions.containsKey(name)) {
            throw IllegalStateException("attempted to add region '${name}' but a region with the same name exists")
        }

        val region = Region(name, instance)

        regions[name] = region
        return region
    }

    /**
     * Removes a [Region]
     *
     * <p> Calling methods of a removed region or using it as a
     * parameter will throw an exception.
     *
     * @param name The unique name of the region
     *
     * @throws IllegalStateException If the given region's name
     * does not exist
     */
    fun remove(name: String) {
        regions as MutableMap

        if(!regions.containsKey(name)) {
            throw IllegalStateException("Region '${name}' does not exist")
        }

        regions.remove(name)
    }
}