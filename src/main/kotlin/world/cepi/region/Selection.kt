package world.cepi.region

import net.minestom.server.entity.Entity
import net.minestom.server.instance.Instance
import net.minestom.server.utils.BlockPosition

class Selection(var pos1: BlockPosition, var pos2: BlockPosition) {

    fun isInside(instance: Instance, position: BlockPosition): Boolean {
        return (pos1.x..pos2.x).contains(position.x) ||
                (pos1.y..pos2.y).contains(position.y) ||
                (pos1.z..pos2.z).contains(position.z)
    }

    fun isInside(instance: Instance, entity: Entity): Boolean {
        return isInside(instance, entity.position.toBlockPosition())
    }

    fun findInside(instance: Instance): Collection<Entity> {
        return instance.entities.filter { isInside(instance, it) }
    }
}