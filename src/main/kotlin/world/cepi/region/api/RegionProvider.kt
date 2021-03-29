package world.cepi.region.api

/**
 * Represents a RegionAPI implementation.
 *
 * <p> Current version: 1.0
 *
 * @since RegionAPI 1.0
 */
object RegionProvider {

    val pools: Set<RegionPool> = mutableSetOf()

    /**
     * Gets the [RegionPool] with the specified name,
     * or null, if it doesn't exist.
     *
     * @param name The specified name
     *
     * @return The pool, with the given name, or null.
     */
    operator fun get(name: String): RegionPool? {
        for (pool in pools) {
            if (pool.name == name) return pool
        }
        return null
    }

}