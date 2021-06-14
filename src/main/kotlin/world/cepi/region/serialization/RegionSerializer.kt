package world.cepi.region.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import world.cepi.kstom.Manager
import world.cepi.region.Selection
import world.cepi.region.api.Region

@Serializable
@SerialName("Region")
private class RegionSurrogate(
    val name: String,
    val selections: List<Selection>
)

object RegionSerializer : KSerializer<Region> {
    override val descriptor: SerialDescriptor = RegionSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Region) {
        val surrogate = RegionSurrogate(value.name, value.selections)
        encoder.encodeSerializableValue(RegionSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): Region {
        val surrogate = decoder.decodeSerializableValue(RegionSurrogate.serializer())
        return Region(surrogate.name, Manager.instance.instances.first()).apply {
            surrogate.selections.forEach { addSelection(it) }
        }
    }

}