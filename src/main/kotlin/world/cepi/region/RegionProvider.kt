package world.cepi.region

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * Represents an object that provides the [Region]s API.
 *
 * Current version: 1.0
 */
interface RegionProvider {

    /**
     * @return The Regions the API version this
     * implementation supports.
     */
    @NotNull
    fun getVersion(): String

    /**
     * @return The name of this Regions API
     * implementation.
     */
    @NotNull
    fun getImplementationName(): String

    /**
     * @return An unmodifiable collection representation
     * of all the [RegionPool]s managed by this provider.
     */
    @NotNull
    fun getPools(): Collection<RegionPool>

    /**
     * Gets the [RegionPool] with the specified name,
     * or null, if it doesn't exist.
     *
     * @param name The specified name
     *
     * @return The pool, with the given name, or null.
     */
    @Nullable
    fun getPool(@NotNull name: String): RegionPool?

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
    @NotNull
    fun createPool(@NotNull name: String): RegionPool

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
    fun removePool(@NotNull pool: RegionPool)

}