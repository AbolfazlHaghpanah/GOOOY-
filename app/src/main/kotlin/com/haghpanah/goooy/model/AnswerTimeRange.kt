package com.haghpanah.goooy.model

import kotlinx.serialization.Serializable

@Serializable
data class AnswerTimeRange(
    val startIn24: Int,
    val endIn24: Int,
)
