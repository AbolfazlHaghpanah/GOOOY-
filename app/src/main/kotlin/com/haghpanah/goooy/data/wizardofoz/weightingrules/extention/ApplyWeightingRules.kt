package com.haghpanah.goooy.data.wizardofoz.weightingrules.extention

import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRules
import com.haghpanah.goooy.model.answer.Answer

fun List<Answer>.applyWeightingRules(
    rules: List<WeightingRules>,
): List<Answer> {
    rules.sortedBy {
        it.mode.order
    }.forEach { rules ->
        forEach { answer ->
            rules.applyWeight(answer)
        }
    }

    return this
}
