package com.haghpanah.goooy.data.wizardofoz.weightingrules.rules

import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRules
import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRulesMode
import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRulesMode.INITIATE
import com.haghpanah.goooy.model.answer.Answer
import com.haghpanah.goooy.model.answer.AnswerType.Natural

class DontBeNormal() : WeightingRules() {
    override fun matches(answer: Answer): Boolean {
        return answer.type == Natural
    }

    override val weightIfMatches: Int = 5
    override val mode: WeightingRulesMode = INITIATE
}