package world.cepi.region;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a collection of [Region]s. Regions in different pools
 * can overlap, but regions inside the same pool cannot.
 *
 * Regions in the same pool also need to have unique names.
 */
interface RegionPool {

    /**
     * The name of this region pool.
     */
    @NotNull
    fun getName(): String

    /**
     * Gets an unmodifiable collection representation of all the
     * [Region]s contained inside this pool.
     *
     * @return A collection of regions inside this pool
     */
    @NotNull
    fun getRegions(): Collection<Region>

    /**
     * Checks if a given [Region] resides inside this
     * pool.
     *
     * @param region The given region
     *
     * @return True, if given region is inside this pool.
     * False otherwise.
     */
    fun contains(region: Region): Boolean

    /**
     * @return The amount of [Region]s inside this pool.
     */
    fun size(): Int

    /**
     * Gets the [Region] inside this pool with the given name.
     *
     * @param name The given name
     *
     * @return The region inside this pool with that name,
     * or null, if it doesn't exist.
     */
    @Nullable
    fun getRegion(@NotNull name: String): Region

    /**
     * Creates a new [Region] inside this region pool.
     *
     * The creates region doesn't have any volume to begin with.
     *
     * @param name The unique name of the region
     *
     * @return The created region
     *
     * @throws IllegalStateException If the name provided was not unique.
     */
    @NotNull
    fun createRegion(@NotNull name: String);

    /**
     * Removes a given [Region] from this pool.
     *
     * <p> Calling methods of a removed region or using it as a
     * parameter will throw an exception.
     *
     * @param region The given region
     *
     * @throws IllegalStateException If the given region was not part of
     * this pool.
     */
    fun remove(@NotNull region: Region)

}
