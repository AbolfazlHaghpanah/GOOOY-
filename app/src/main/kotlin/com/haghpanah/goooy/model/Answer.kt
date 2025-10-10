package com.haghpanah.goooy.model

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
    var weight: Int = when (type) {
        AnswerType.Positive -> 10
        AnswerType.Negative -> 10
        AnswerType.Silly -> 1
        AnswerType.Natural -> 3
    }
}
