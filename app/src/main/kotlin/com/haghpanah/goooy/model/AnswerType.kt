package com.haghpanah.goooy.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = AnswerTypeSerializer::class)
enum class AnswerType(val value: String) {
    Positive("positive"),
    Negative("negative"),
    Silly("silly"),
    Natural("natural"),
}

class AnswerTypeSerializer() : KSerializer<AnswerType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "type",
        PrimitiveKind.STRING
    )

    override fun serialize(
        encoder: Encoder,
        value: AnswerType,
    ) {
        encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): AnswerType {
        val stringValue = decoder.decodeString()
        return AnswerType.entries
            .firstOrNull { it.value == stringValue }
            ?: error("Unknown AnswerType: $stringValue")
    }
}