package world.cepi.region.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minestom.server.instance.Instance
import world.cepi.kstom.Manager
import world.cepi.region.Selection
import world.cepi.region.api.Region
import world.cepi.region.api.RegionSnapshot

object InstanceSerializer : KSerializer<Instance> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Instance) {
        encoder.encodeString("*")
    }

    override fun deserialize(decoder: Decoder): Instance {
        return Manager.instance.instances.first()
    }

}