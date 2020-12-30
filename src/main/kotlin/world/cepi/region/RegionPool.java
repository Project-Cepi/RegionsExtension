package world.cepi.region;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Represents a collection of regions. Regions in different pools
 * can overlap, but regions inside the same pool cannot.
 *
 * Regions in the same pool also need to have unique names.
 */
public interface RegionPool extends Iterable<Region> {

    /**
     * Gets an unmodifiable collection representation of all the
     * {@link Region}s contained inside this pool.
     *
     * @return A collection of regions inside this pool
     */
    @NotNull
    Collection<Region> getRegions();

    /**
     * Checks if a given {@link Region} resides inside this
     * pool.
     *
     * @param region The given region
     *
     * @return True, if given region is inside this pool.
     * False otherwise.
     */
    boolean contains(Region region);

    /**
     * @return The amount of {@link Region}s inside this pool.
     */
    int size();

    /**
     * Gets the {@link Region} inside this pool with the given name.
     *
     * @param name The given name
     *
     * @return The region inside this pool with that name,
     * or null, if it doesn't exist.
     */
    @Nullable
    Region getRegion(@NotNull String name);

    /**
     * Creates a new {@link Region} inside this region pool.
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
    Region createRegion(@NotNull String name) throws IllegalStateException;

    /**
     * Removes a given {@link Region} from this pool.
     *
     * <p> Calling methods of a removed region or using it as a
     * parameter will throw an exception.
     *
     * @param region The given region
     *
     * @throws IllegalStateException If the given region was not part of
     * this pool.
     */
    void remove(@NotNull Region region) throws IllegalStateException;

}
