package world.cepi.region.api

/**
 * Represents a RegionAPI implementation.
 *
 * <p> Current version: 1.0
 *
 * @since RegionAPI 1.0
 */
interface RegionProvider {

    /**
     * The name of this Regions API
     * implementation.
     */
    val implementationName: String

    /**
     * @return An unmodifiable collection representation
     * of all the [RegionPool]s managed by this provider.
     */
    val pools: Collection<RegionPool>

    /**
     * Gets the [RegionPool] with the specified name,
     * or null, if it doesn't exist.
     *
     * @param name The specified name
     *
     * @return The pool, with the given name, or null.
     */
    operator fun get(name: String): RegionPool?

    /**
     * Creates a new [RegionPool], with the given name.
     *
     * @param name The given name
     *
     * @return The new pool.
     *
     * @throws IllegalStateException If a pool with the same
     * name already exists.
     */
    fun createPool(name: String): RegionPool

    /**
     * Removes the given [RegionPool].
     *
     * <p> Calling methods of a removed [RegionPool] will
     * cause an exception to be thrown.
     *
     * @param pool The given pool
     *
     * @throws IllegalStateException If the pool is already
     * removed.
     */
    fun removePool(pool: RegionPool)

}