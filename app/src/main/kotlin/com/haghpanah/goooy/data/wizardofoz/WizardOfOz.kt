package com.haghpanah.goooy.data.wizardofoz

import com.haghpanah.goooy.model.answer.Answer

interface WizardOfOz {
    fun getAnswer(): Answer
    fun decreaseAnswerWeight(answerId: Int)
}