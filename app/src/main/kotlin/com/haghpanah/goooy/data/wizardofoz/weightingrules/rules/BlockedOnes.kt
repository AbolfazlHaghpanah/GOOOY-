package com.haghpanah.goooy.data.wizardofoz.weightingrules.rules

import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRules
import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRulesMode
import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRulesMode.Explicit
import com.haghpanah.goooy.model.answer.Answer

class BlockedOnes(
    private val blockedAnswersIds: List<Int>,
) : WeightingRules() {
    override fun matches(answer: Answer): Boolean {
        return blockedAnswersIds.contains(answer.id)
    }

    override val weightIfMatches: Int = 1
    override val mode: WeightingRulesMode = Explicit
}