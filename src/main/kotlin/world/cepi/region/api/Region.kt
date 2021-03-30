package world.cepi.region.api

import net.minestom.server.data.Data
import net.minestom.server.data.DataContainer
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition
import world.cepi.region.Selection

/**
 * Represents a 3-dimensional non-uniform region.
 * (Though it can be uniform, if you define it like so.)
 */
data class Region(
    /**
     * The unique name of this region.
     */
    val name: String,
    /**
     * The [RegionPool] in which this region
     * resided in. Or null, if this region has been removed.
     */
    val pool: RegionPool,
) : DataContainer {

    private var _data: Data? = null

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
     *
     * @return True, only if the block is inside this region.
     */
    fun isInside(pos: BlockPosition): Boolean
        = selections.any { it.contains(pos) }

    /**
     * Adds the given selection to this region.
     *
     * @param selection The selection to add
     *
     * @return The amount of blocks that were added in total. This can be less than the
     * actual selection, if part of the selected area was already in the region.
     */
    fun addBlocks(selection: Selection): Int {

        return if (!selections.any { it.containsAll(selection) }) {

            selections as MutableList

            selections.add(selection)

            // TODO
            0

        } else 0
    }

    /**
     * Finds regions that are inside this [selection] and removes them
     *
     * @param selection The selection to remove.
     */
    private fun simplifyWith(selection: Selection) {
        selections.filter { selection.containsAll(it) }.forEach {
            selections as MutableList
            selections.remove(it)
        }
    }

    /**
     * Removes the given selection from this region
     *
     * @param selection The selection to add
     *
     * @return The amount of blocks that were removed in total. This can be less than the
     * actual selection, if the selected area was not entirely inside the region.
     */
    fun removeBlocks(selection: Selection): Int {
        // Remove all selections that
        simplifyWith(selection)
    }

    /**
     * A collection of all the entities by class
     * currently inside this region.
     *
     * @param T the class to check against
     */
    inline fun <reified T: Entity> findEntities(): Set<T> =
        selections.map { it.find(pool.instance) }.flatten().filterIsInstance(T::class.java).toSet()

    /**
     * Creates a collection of all the entities which type
     * matches at least one of the given types, and that are currently
     * inside this region.
     *
     * @param types The given entity types. Null not allowed as an member.
     *
     * @return Collection of entities inside this region with a given type
     */
    fun findEntitiesByType(vararg types: EntityType): Collection<Entity> =
        findEntities<Entity>().filter { types.contains(it.entityType) }.toMutableList()

    override fun getData(): Data? = _data

    override fun setData(data: Data?) {
        _data = data
    }

}
