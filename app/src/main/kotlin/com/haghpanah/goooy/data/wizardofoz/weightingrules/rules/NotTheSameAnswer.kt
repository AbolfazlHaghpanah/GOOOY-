package com.haghpanah.goooy.data.wizardofoz.weightingrules.rules

import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRules
import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRulesMode
import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRulesMode.Explicit
import com.haghpanah.goooy.model.Answer

class NotTheSameAnswer(
    private val seenAnswersIds: List<Int>,
) : WeightingRules() {
    override fun matches(answer: Answer): Boolean {
        return seenAnswersIds.contains(answer.id)
    }

    override val weightIfMatches: Int = 3
    override val mode: WeightingRulesMode = Explicit
}