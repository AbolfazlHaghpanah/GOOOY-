package com.haghpanah.goooy.data.wizardofoz.weightingrules.rules

import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRules
import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRulesMode
import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRulesMode.INITIATE
import com.haghpanah.goooy.model.Answer
import com.haghpanah.goooy.model.AnswerType

class AllNegative() : WeightingRules() {
    override fun matches(answer: Answer): Boolean {
        return answer.type == AnswerType.Negative
    }

    override val mode: WeightingRulesMode = INITIATE

    override val weightIfMatches: Int = 20
}