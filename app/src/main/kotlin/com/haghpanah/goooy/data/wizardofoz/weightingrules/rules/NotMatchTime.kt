package com.haghpanah.goooy.data.wizardofoz.weightingrules.rules

import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRules
import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRulesMode
import com.haghpanah.goooy.data.wizardofoz.weightingrules.WeightingRulesMode.Explicit
import com.haghpanah.goooy.model.answer.Answer
import java.util.Calendar

class NotMatchTime : WeightingRules() {

    override fun matches(answer: Answer): Boolean {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
        val currentTimeIn24 = calendar.get(Calendar.HOUR_OF_DAY)

        return answer.dayTimeRange != null && currentTimeIn24 !in answer.dayTimeRange.startIn24..answer.dayTimeRange.endIn24
    }

    override val mode: WeightingRulesMode = Explicit

    override val weightIfMatches: Int = 0
}