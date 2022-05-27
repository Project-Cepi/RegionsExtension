package world.cepi.region.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minestom.server.instance.Instance
import world.cepi.kstom.Manager

object InstanceSerializer : KSerializer<Instance> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Instance) {
        encoder.encodeString("*")
    }

    override fun deserialize(decoder: Decoder): Instance {
        return Manager.instance.instances.first()
    }

}