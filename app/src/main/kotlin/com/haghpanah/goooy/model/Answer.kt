package com.haghpanah.goooy.model

import androidx.compose.ui.graphics.Color

interface Answer {
    val emoji: String
    val contentTextId: Int
    val color: Color
}

data class PositiveAnswer(
    override val emoji: String,
    override val contentTextId: Int,
) : Answer {
    override val color: Color = Color(0xFF4CAF50)
}

data class NaturalAnswer(
    override val emoji: String,
    override val contentTextId: Int,
) : Answer {
    override val color: Color = Color(0xFF9E9E9E)
}

data class NegativeAnswer(
    override val emoji: String,
    override val contentTextId: Int,
) : Answer {
    override val color: Color = Color(0xFFF44336)
}

data class SillyAnswer(
    override val emoji: String,
    override val contentTextId: Int,
) : Answer {
    override val color: Color = Color(0xFF9C27B0)
}

