package com.haghpanah.goooy.data.wizardofoz

import com.haghpanah.goooy.model.answer.Answer

interface WizardOfOz {
    suspend fun getAnswer(): Answer
    suspend fun decreaseAnswerWeight(answerId: Int)
}