package world.cepi.region.api

import net.minestom.server.data.DataContainer
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition
import world.cepi.region.Selection

/**
 * Represents a 3-dimensional non-uniform region.
 * (Though it can be uniform, if you define it like so.)
 */
class Region(
    /**
     * The unique name of this region.
     */
    val name: String,
    /**
     * The [RegionPool] in which this region
     * resided in. Or null, if this region has been removed.
     */
    val pool: RegionPool,
    /**
     * An unmodifiable collection of worlds (Minestom [Instance]s),
     * that contain at least some part of this region.
     */
    val instance: Instance
) : DataContainer {

    /**
     * List of all selections
     */
    val selections: List<Selection> = mutableListOf()

    /**
     * True, if this region contains at least one block.
     * (Has any size.) False otherwise.
     */
    val defined: Boolean
        get() = selections.isEmpty()

    /**
     * The volume of this region in blocks.
     *
     * TODO implement it
     */
    val volume: Int = 0

    /**
     * Checks if the given block position is inside of this
     * region in the given [Instance].
     *
     * @param pos, The block position
     * @param world, the Instance
     *
     * @return True, only if the block is inside this region.
     */
    fun isInside(pos: BlockPosition, world: Instance): Boolean
        = selections.any { it.isInside(instance, pos) }

    /**
     * Adds the given selection to this region.
     *
     * @param selection The selection to add
     *
     * @return The amount of blocks that were added in total. This can be less than the
     * actual selection, if part of the selected area was already in the region.
     */
    fun addBlocks(selection: Selection): Int {
        selections as MutableList

        selections.add(selection)
    }

    /**
     * Removes the given selection from this region
     *
     * @param selection The selection to add
     *
     * @return The amount of blocks that were removed in total. This can be less than the
     * actual selection, if the selected area was not entirely inside the region.
     */
    fun removeBlocks(selection: Selection): Int

    /**
     * Creates an iterator that iterates through the block
     * positions inside the given chunk that are inside this region.
     *
     * @param chunkX The chunk x-coordinate
     * @param chunkZ The chunk z-coordinate
     *
     * @return Iterator for blocks inside this region in the chunk.
     */
    fun iterateChunk(chunkX: Int, chunkZ: Int) : Iterator<BlockPosition>

    /**
     * A collection of all the players that are
     * currently inside this region.
     */
    val players: Set<Player>
        get() = entities.filterIsInstance<Player>().toSet()

    /**
     * A collection of all the entities that are
     * currently inside this region.
     */
    val entities: Set<Entity>
        get() = selections.map { it.findInside(instance) }.flatten().toSet()

    /**
     * Creates a collection of all the entities which type
     * matches at least one of the given types, and that are currently
     * inside this region.
     *
     * @param types The given entity types. Null not allowed as an member.
     *
     * @return Collection of entities inside this region with a given type
     */
    fun entities(vararg types: EntityType): Collection<Entity> =
        entities.filter { types.contains(it.entityType) }.toMutableList()

}
