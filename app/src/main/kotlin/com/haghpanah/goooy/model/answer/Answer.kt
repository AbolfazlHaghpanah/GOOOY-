package com.haghpanah.goooy.model.answer

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val id: Int,
    val dayTimeRange: AnswerTimeRange?,
    val type: AnswerType,
    val description: String,
    val name: String,
    val emoji: String,
) {
    var weight: Float = when (type) {
        AnswerType.Positive -> 1f
        AnswerType.Negative -> 1f
        AnswerType.Silly -> 0.1f
        AnswerType.Natural -> 0.3f
    }
}
