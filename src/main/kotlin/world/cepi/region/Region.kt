package world.cepi.region;

import net.minestom.server.data.DataContainer
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a 3-dimensional non-uniform region.
 * (Though it can be uniform, if you define it like so.)
 *
 * @since RegionAPI 1.0
 */
interface Region : DataContainer {

    /**
     * @return The unique name of this region.
     *
     * @since RegionAPI 1.0
     */
    fun getName(): String

    /**
     * @return The [RegionPool] in which this region
     * resided in. Or null, if this region has been removed.
     *
     * @since RegionAPI 1.0
     */
    fun getPool(): RegionPool

    /**
     * @return An unmodifiable collection of worlds (Minestom [Instance]s),
     * that contain at least some part of this region.
     *
     * @since RegionAPI 1.0
     */
    fun getWorlds(): Collection<Instance>

    /**
     * @return True, if this region contains at least one block.
     * (Has any size.) False otherwise.
     *
     * @since RegionAPI 1.0
     */
    fun isDefined(): Boolean

    /**
     * @return The volume of this region in blocks.
     *
     * @since RegionAPI 1.0
     */
    fun getVolume(): Int

    /**
     * Adds the given selection to this region.
     *
     * @param pos1 The first corner of the selection
     * @param pos2 The second corner of the selection
     * @param world The [Instance] where the selection is supposed to be in.
     *
     * @return The amount of blocks that were added in total. This can be less than the
     * actual selection, if part of the selected area was already in the region.
     *
     * @since RegionAPI 1.0
     */
    fun addBlocks(pos1: BlockPosition, pos2: BlockPosition, world: Instance): Int

    /**
     * Removes the given selection from this region
     *
     * @param pos1 The first corner of the selection
     * @param pos2 The second corner of the selection
     * @param world The [Instance] where the selection is supposed to be in.
     *
     * @return The amount of blocks that were removed in total. This can be less than the
     * actual selection, if the selected area was not entirely inside the region.
     *
     * @since RegionAPI 1.0
     */
    fun removeBlocks(pos1: BlockPosition, pos2: BlockPosition, world: Instance): Int

    /**
     * Creates a collection of all the players that are
     * currently inside this region.
     *
     * @return Collection of players inside this region
     *
     * @since RegionAPI 1.0
     */
    fun getPlayers(): MutableCollection<Player>

    /**
     * Creates a collection of all the entities that are
     * currently inside this region.
     *
     * @return Collection of entities inside this region
     *
     * @since RegionAPI 1.0
     */
    fun getEntities(): MutableCollection<Entity>

    /**
     * Creates a collection of all the entities which type
     * matches at least one of the given types, and that are currently
     * inside this region.
     *
     * @param types The given entity types. Null not allowed as an member.
     *
     * @return Collection of entities inside this region with a given type
     *
     * @since RegionAPI 1.0
     */
    fun getEntities(vararg types: EntityType): MutableCollection<Entity>

}