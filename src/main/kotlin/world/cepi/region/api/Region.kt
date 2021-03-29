package world.cepi.region.api

import net.minestom.server.data.DataContainer
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition

/**
 * Represents a 3-dimensional non-uniform region.
 * (Though it can be uniform, if you define it like so.)
 *
 * @since RegionAPI 1.0
 */
interface Region : DataContainer {

    /**
     * The unique name of this region.
     *
     * @since RegionAPI 1.0
     */
    val name: String

    /**
     * The [RegionPool] in which this region
     * resided in. Or null, if this region has been removed.
     *
     * @since RegionAPI 1.0
     */
    val pool: RegionPool

    /**
     * An unmodifiable collection of worlds (Minestom [Instance]s),
     * that contain at least some part of this region.
     *
     * @since RegionAPI 1.0
     */
    val instances: Collection<Instance>

    /**
     * True, if this region contains at least one block.
     * (Has any size.) False otherwise.
     *
     * @since RegionAPI 1.0
     */
    val defined: Boolean

    /**
     * The volume of this region in blocks.
     *
     * @since RegionAPI 1.0
     */
    val volume: Int

    /**
     * Checks if the given block position is inside of this
     * region in the given [Instance].
     *
     * @param pos, The block position
     * @param world, the Instance
     *
     * @return True, only if the block is inside this region.
     *
     * @since RegionAPI 1.0
     */
    fun isInside(pos: BlockPosition, world: Instance): Boolean

    /**
     * Adds the given selection to this region.
     *
     * @param pos1 The first corner of the selection
     * @param pos2 The second corner of the selection
     * @param instance The [Instance] where the selection is supposed to be in.
     *
     * @return The amount of blocks that were added in total. This can be less than the
     * actual selection, if part of the selected area was already in the region.
     *
     * @since RegionAPI 1.0
     */
    fun addBlocks(pos1: BlockPosition, pos2: BlockPosition, instance: Instance): Int

    /**
     * Removes the given selection from this region
     *
     * @param pos1 The first corner of the selection
     * @param pos2 The second corner of the selection
     * @param instance The [Instance] where the selection is supposed to be in.
     *
     * @return The amount of blocks that were removed in total. This can be less than the
     * actual selection, if the selected area was not entirely inside the region.
     *
     * @since RegionAPI 1.0
     */
    fun removeBlocks(pos1: BlockPosition, pos2: BlockPosition, instance: Instance): Int

    /**
     * Creates an iterator that iterates through the block
     * positions inside the given chunk that are inside this region.
     *
     * @param chunkX The chunk x-coordinate
     * @param chunkZ The chunk z-coordinate
     * @param world The [Instance] where the chunk resides.
     *
     * @return Iterator for blocks inside this region in the chunk.
     *
     * @since RegionAPI 1.0
     */
    fun iterateChunk(chunkX: Int, chunkZ: Int, world: Instance) : Iterator<BlockPosition>

    /**
     * A collection of all the players that are
     * currently inside this region.
     *
     * @since RegionAPI 1.0
     */
    val players: Collection<Player>

    /**
     * A collection of all the entities that are
     * currently inside this region.
     *
     * @since RegionAPI 1.0
     */
    val entities: Collection<Entity>

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
    fun entities(vararg types: EntityType): Collection<Entity> =
        entities.filter { types.contains(it.entityType) }.toMutableList()

}
