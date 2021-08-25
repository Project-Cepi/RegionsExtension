package world.cepi.region.api

import kotlinx.serialization.Serializable
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.minestom.server.coordinate.Pos
import net.minestom.server.data.DataImpl
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.event.EventFilter
import net.minestom.server.event.EventNode
import net.minestom.server.instance.Instance
import net.minestom.server.tag.Tag
import net.minestom.server.tag.TagHandler
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import world.cepi.kstom.event.listenOnly
import world.cepi.kstom.item.get
import world.cepi.kstom.item.set
import world.cepi.kstom.serializer.BossBarSerializer
import world.cepi.kstom.serializer.ComponentSerializer
import world.cepi.kstom.serializer.NBTSerializer
import world.cepi.region.Selection
import world.cepi.region.event.PlayerRegionUpdateEvent
import world.cepi.region.serialization.InstanceSerializer
import world.cepi.region.size

/**
 * Represents a 3-dimensional non-uniform region.
 * (Though it can be uniform, if you define it like so.)
 */
@Serializable
data class Region(
    /** The unique name of this region. */
    val name: String,

    /** The [Instance] in which this region resides in. */
    @Serializable(with = InstanceSerializer::class)
    val instance: Instance,

    /** List of all selections */
    val selections: MutableList<Selection> = mutableListOf(),

    /** The display name of this region. */
    @Serializable(with = ComponentSerializer::class)
    var displayName: Component? = null,

    /** If this region's name is hidden */
    var hidden: Boolean = false
) : TagHandler {

    /**
     * True, if this region contains at least one block.
     * (Has any size.) False otherwise.
     */
    val defined: Boolean
        get() = selections.isEmpty()

    @Serializable(with = NBTSerializer::class)
    private val nbtCompound = NBTCompound()

    val snapshots: MutableList<RegionSnapshot> = mutableListOf()

    /**
     * The volume of this region in cubic meters.
     */
    val volume: Int
        get() = selections.map { it.xRange.size * it.yRange.size * it.zRange.size }.sum()

    /**
     * Checks if the given block position is inside of this
     * region in the given [Instance].
     *
     * @param pos, The block position
     *
     * @return True, only if the block is inside this region.
     */
    fun isInside(pos: Pos): Boolean
        = selections.any { it.contains(pos.asVec()) }

    /**
     * Adds the given selection to this region.
     *
     * @param selection The selection to add
     *
     * @return The amount of blocks that were added in total. This can be less than the
     * actual selection, if part of the selected area was already in the region.
     */
    fun addSelection(selection: Selection): Int {

        simplifyWith(selection)

        return if (!selections.any { it.containsAll(selection) }) {

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
    fun removeSelection(selection: Selection): Int {
        // Remove all selections that are in this selection
        simplifyWith(selection)

        return 0 // TODO
    }

    /**
     * A collection of all the entities by class
     * currently inside this region.
     *
     * @param T the class to check against
     */
    inline fun <reified T: Entity> findEntities(): Set<T> =
        selections.map { it.find(instance) }.flatten().filterIsInstance(T::class.java).toSet()

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

    companion object {
        val blankRegionName = Component.text("Somewhere")

        val noPassRegions = mutableListOf<Region>()
        val noPassnode = EventNode.event("region-no-pass", EventFilter.PLAYER) { true }

        init {
            noPassnode.listenOnly<PlayerRegionUpdateEvent> {
                if (noPassRegions.contains(newRegion)) isCancelled = true
            }
        }
    }

    override fun <T : Any?> getTag(tag: Tag<T>): T? {
        return tag.read(nbtCompound)
    }

    override fun <T : Any?> setTag(tag: Tag<T>, value: T?) {
        tag.write(nbtCompound, value)
    }
}

fun Player.showRegion(region: Region?) {
    get<BossBar>("regions-bossbar", serializer = BossBarSerializer)?.let {
        hideBossBar(it)
    }

    val bossBar = BossBar.bossBar(
        region?.let {
            if (it.hidden) return@let null

            it.displayName ?: Component.text(it.name)
        } ?: Region.blankRegionName,
        0f,
        BossBar.Color.PINK,
        BossBar.Overlay.PROGRESS
    )

    showBossBar(bossBar)

    set("regions-bossbar", BossBarSerializer, bossBar)
}