package com.haghpanah.goooy.data.wizardofoz.weightingrules

import com.haghpanah.goooy.model.Answer

abstract class WeightingRules() {
    protected abstract fun matches(answer: Answer): Boolean
    protected abstract val weightIfMatches: Int
    abstract val mode: WeightingRulesMode

    fun applyWeight(answer: Answer) {
        if (matches(answer)) {
            answer.weight = weightIfMatches
        }
    }
}