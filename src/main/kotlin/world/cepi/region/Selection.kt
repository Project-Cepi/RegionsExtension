package world.cepi.region

import net.minestom.server.entity.Entity
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition

/**
 * Represents two block positions and the region between them.
 */
data class Selection(
    var pos1: BlockPosition,
    var pos2: BlockPosition
) {

    fun contains(position: BlockPosition): Boolean {
        return (pos1.x..pos2.x).contains(position.x) ||
                (pos1.y..pos2.y).contains(position.y) ||
                (pos1.z..pos2.z).contains(position.z)
    }

    /**
     * Check if this selection contains the entity's position
     *
     * @param entity The entity to check against
     *
     * @return If this entity's position is in this selection.
     */
    fun contains(entity: Entity): Boolean {
        return contains(entity.position.toBlockPosition())
    }

    /**
     * Check if this [Selection] contains the full [selection].
     *
     * @param selection The selection to check against
     *
     * @return If this [selection] is fully contained inside this [Selection]
     */
    fun containsAll(selection: Selection): Boolean {
        return contains(selection.pos1) && contains(selection.pos2)
    }

    /**
     * Check if this [Selection] intersects with another [selection]
     *
     * @param selection The [Selection] to check against
     *
     * @return If any part of the [Selection] intersects with the other [selection]
     */
    fun containsSome(selection: Selection): Boolean {

    }

    /**
     * Finds all entities in an [instance] that are in this [Selection]
     *
     * @param instance The instance to check against
     *
     * @return A list of entities who are in this [Selection]
     */
    fun find(instance: Instance): List<Entity> {
        return instance.entities.filter { contains(it) }
    }
}