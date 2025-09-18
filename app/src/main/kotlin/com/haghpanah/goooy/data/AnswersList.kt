package com.haghpanah.goooy.data

import com.haghpanah.goooy.R
import com.haghpanah.goooy.model.Answer
import com.haghpanah.goooy.model.NaturalAnswer
import com.haghpanah.goooy.model.NegativeAnswer
import com.haghpanah.goooy.model.PositiveAnswer
import com.haghpanah.goooy.model.SillyAnswer

val answers: List<Answer> = listOf(
    // ✅ Positive
    PositiveAnswer("🍀", R.string.ans_yes_absolutely),
    PositiveAnswer("🤟", R.string.ans_for_sure_bro),
    PositiveAnswer("🔥", R.string.ans_its_happening),
    PositiveAnswer("✨", R.string.ans_no_doubts),
    PositiveAnswer("🏆", R.string.ans_guaranteed),

    // ❌ Negative
    NegativeAnswer("🚫", R.string.ans_no_chance),
    NegativeAnswer("🙅", R.string.ans_forget_it),
    NegativeAnswer("😡", R.string.ans_hell_no),
    NegativeAnswer("❌", R.string.ans_never_ever),
    NegativeAnswer("😤", R.string.ans_not_today),

    // ⚪ Neutral
    NaturalAnswer("🤔", R.string.ans_maybe),
    NaturalAnswer("⏳", R.string.ans_ask_again_later),
    NaturalAnswer("🌫️", R.string.ans_unclear),
    NaturalAnswer("🎲", R.string.ans_fifty_fifty),
    NaturalAnswer("⚖️", R.string.ans_could_go_either_way),

    // 🤪 Silly
    SillyAnswer("😵‍💫", R.string.ans_too_high),
    SillyAnswer("🥪", R.string.ans_sandwich),
    SillyAnswer("🐱", R.string.ans_ask_cat),
    SillyAnswer("🥔", R.string.ans_potato),
    SillyAnswer("🤯", R.string.ans_gooooy),
)