package world.cepi.region

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition
import world.cepi.kstom.serializer.BlockPositionSerializer
import java.util.*

/**
 * Represents two block positions and the region between them.
 */
@Serializable
data class Selection(
    @Serializable(with = BlockPositionSerializer::class)
    val pos1: BlockPosition,
    @Serializable(with = BlockPositionSerializer::class)
    val pos2: BlockPosition
) {

    @Transient
    val xRange = listOf(pos1.x, pos2.x).sorted().let { it[0]..it[1] }

    @Transient
    val yRange = listOf(pos1.y, pos2.y).sorted().let { it[0]..it[1] }

    @Transient
    val zRange = listOf(pos1.z, pos2.z).sorted().let { it[0]..it[1] }

    fun contains(position: BlockPosition): Boolean =
        xRange.contains(position.x) &&
                yRange.contains(position.y) &&
                zRange.contains(position.z)

    /**
     * Check if this selection contains the entity's position
     *
     * @param entity The entity to check against
     *
     * @return If this entity's position is in this selection.
     */
    fun contains(entity: Entity): Boolean =
        contains(entity.position.toBlockPosition())


    /**
     * Check if this [Selection] contains the full [selection].
     *
     * @param selection The selection to check against
     *
     * @return If this [selection] is fully contained inside this [Selection]
     */
    fun containsAll(selection: Selection): Boolean =
        contains(selection.pos1) && contains(selection.pos2)


    /**
     * Check if this [Selection] intersects with another [selection]
     *
     * @param selection The [Selection] to check against
     *
     * @return If any part of the [Selection] intersects with the other [selection]
     */
    fun containsSome(selection: Selection): Boolean =
        xRange.contains(selection.pos1.x..selection.pos2.x) &&
                yRange.contains(selection.pos1.y..selection.pos2.y) &&
                zRange.contains(selection.pos1.z..pos2.z)

    /**
     * Finds all entities in an [instance] that are in this [Selection]
     *
     * @param instance The instance to check against
     *
     * @return A list of entities who are in this [Selection]
     */
    fun find(instance: Instance): List<Entity> =
        instance.entities.filter { contains(it) }

    // Probably should move this into a utility class
    /**
     * Check if an [IntRange] has values which are contained within this [IntRange]
     *
     * @param value The [IntRange] to check
     *
     * @return If parts of the [IntRange] are contained within this [IntRange]
     */
    fun IntRange.contains(value: IntRange): Boolean =
        (this.first >= value.last) && (this.last <= value.first)

    companion object {
        val selections: Map<UUID, Selection> = mutableMapOf()

        operator fun get(player: Player): Selection {
            selections as MutableMap

            return if (selections.contains(player.uuid)) {
                selections[player.uuid]!!
            } else {
                val selection = Selection(BlockPosition(0, 0, 0), BlockPosition(0, 0, 0)) // Placeholder positions
                selections[player.uuid] = selection
                selection
            }
        }

        fun pos1(player: Player, blockPosition: BlockPosition) {
            selections as MutableMap

            selections[player.uuid] = Selection(blockPosition, this[player].pos2)
        }

        fun pos2(player: Player, blockPosition: BlockPosition) {
            selections as MutableMap

            selections[player.uuid] = Selection(this[player].pos1, blockPosition)
        }
    }
}