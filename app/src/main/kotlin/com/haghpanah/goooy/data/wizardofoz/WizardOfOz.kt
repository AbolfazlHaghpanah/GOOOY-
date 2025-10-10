package com.haghpanah.goooy.data.wizardofoz

import com.haghpanah.goooy.model.Answer

interface WizardOfOz {
    val blockedAnswerIds : List<Int>
    val seenAnswerIds : List<Int>
    fun getAnswer(): Answer
    fun addAnswerToBlockedList(answerId: Int)
}