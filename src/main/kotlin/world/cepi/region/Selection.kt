package world.cepi.region

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.instance.Instance
import world.cepi.kstom.serializer.PositionSerializer
import world.cepi.kstom.serializer.VectorSerializer
import java.util.*

/**
 * Represents two block positions and the region between them.
 */
@Serializable
data class Selection(
    @Serializable(with = VectorSerializer::class)
    val pos1: Vec,
    @Serializable(with = VectorSerializer::class)
    val pos2: Vec
) {

    @Transient
    val xRange = listOf(pos1.x().toInt(), pos2.x().toInt()).sorted().let { it[0]..it[1] }

    @Transient
    val yRange = listOf(pos1.y().toInt(), pos2.y().toInt()).sorted().let { it[0]..it[1] }

    @Transient
    val zRange = listOf(pos1.z().toInt(), pos2.z().toInt()).sorted().let { it[0]..it[1] }

    fun contains(position: Vec): Boolean =
        xRange.contains(position.x().toInt()) &&
                yRange.contains(position.y().toInt()) &&
                zRange.contains(position.z().toInt())

    /**
     * Check if this selection contains the entity's position
     *
     * @param entity The entity to check against
     *
     * @return If this entity's position is in this selection.
     */
    fun contains(entity: Entity): Boolean =
        contains(entity.position.asVec())


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
        xRange.contains(selection.pos1.x().toInt()..selection.pos2.x().toInt()) &&
                yRange.contains(selection.pos1.y().toInt()..selection.pos2.y().toInt()) &&
                zRange.contains(selection.pos1.z().toInt()..pos2.z().toInt())

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

    companion object {
        val selections: Map<UUID, Selection> = mutableMapOf()

        operator fun get(player: Player): Selection {
            selections as MutableMap

            return if (selections.contains(player.uuid)) {
                selections[player.uuid]!!
            } else {
                val selection = Selection(Vec.ZERO, Vec.ZERO) // Placeholder positions
                selections[player.uuid] = selection
                selection
            }
        }

        fun pos1(player: Player, blockPosition: Vec) {
            selections as MutableMap

            selections[player.uuid] = Selection(blockPosition, this[player].pos2)
        }

        fun pos2(player: Player, blockPosition: Vec) {
            selections as MutableMap

            selections[player.uuid] = Selection(this[player].pos1, blockPosition)
        }
    }
}

/**
 * Gets the fully inclusive size of a range
 */
val IntRange.size: Int
    get() = (this.last - this.first) + 1

/**
 * Check if an [IntRange] has values which are contained within this [IntRange]
 *
 * @param value The [IntRange] to check
 *
 * @return If parts of the [IntRange] are contained within this [IntRange]
 */
fun IntRange.contains(value: IntRange): Boolean =
    (this.first >= value.last) && (this.last <= value.first)